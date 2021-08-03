package com.sol.obss_rm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sol.obss_rm.R
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.ui.adapter.CharacterPageAdapter
import com.sol.obss_rm.ui.view.SViewModel
import com.sol.obss_rm.ui.view.SViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: SViewModel by activityViewModels{ SViewModelFactory(Repository()) }
    lateinit var characterPageAdapter : CharacterPageAdapter

    private var isDisplayModeGrid = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterPageAdapter = CharacterPageAdapter()

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = characterPageAdapter

        btn_display.setOnClickListener{
            if(isDisplayModeGrid){
                btn_display.setImageResource(R.drawable.ic_list)
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }else{
                btn_display.setImageResource(R.drawable.ic_grid)
                recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            }

            isDisplayModeGrid = !isDisplayModeGrid
        }

        btn_filter.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_filterFragment)
        }

        txt_reset.setOnClickListener {
            viewModel.isFilter.value = false
            viewModel.getCharacters()
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getByName(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.isFilter.observe(viewLifecycleOwner, {
            txt_reset.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })



        lifecycleScope.launch {
            viewModel.characters.collect() {
                characterPageAdapter.submitData(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getCharacters()
    }
}