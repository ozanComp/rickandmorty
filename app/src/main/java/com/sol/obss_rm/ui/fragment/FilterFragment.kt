package com.sol.obss_rm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sol.obss_rm.R
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.ui.ext.getTextChipChecked
import com.sol.obss_rm.ui.ext.setChipChecked
import com.sol.obss_rm.ui.view.SViewModel
import com.sol.obss_rm.ui.view.SViewModelFactory
import kotlinx.android.synthetic.main.fragment_filter.*

@ExperimentalPagingApi
class FilterFragment : BottomSheetDialogFragment() {
    private val viewModel: SViewModel by activityViewModels{ SViewModelFactory(Repository()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filterValue.observe(viewLifecycleOwner, {
            chipgroup_status.setChipChecked(it[0])
        })

        btn_make_filter.setOnClickListener {
            if(chipgroup_status.getTextChipChecked().isNotEmpty()){
                viewModel.getByStatus(chipgroup_status.getTextChipChecked())
            }

            viewModel.filterValue.value = arrayOf(chipgroup_status.checkedChipId)
            findNavController().popBackStack(R.id.listFragment, false)
        }
    }
}