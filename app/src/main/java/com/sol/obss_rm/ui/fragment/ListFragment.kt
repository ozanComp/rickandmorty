package com.sol.obss_rm.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sol.obss_rm.R
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.ui.adapter.CharacterAdapter
import com.sol.obss_rm.ui.view.SViewModel
import com.sol.obss_rm.ui.view.SViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(R.layout.fragment_list) {
    lateinit var adapter : CharacterAdapter
    private val viewModel: SViewModel by activityViewModels{ SViewModelFactory(Repository()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()

        initOnClick()

        initObserver()


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getCharacters()
    }

    private fun initVariables(){
        adapter = CharacterAdapter()

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
    }

    private fun initOnClick(){
        btn_display.setOnClickListener{
            viewModel.isDisplayModeGrid.value = !viewModel.isDisplayModeGrid.value!!
        }

        btn_filter.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_filterFragment)
        }

        txt_reset.setOnClickListener {
            viewModel.reset()
            viewModel.getCharacters()
        }

        btn_next.setOnClickListener {
            viewModel.nextPage()
        }

        btn_prev.setOnClickListener {
            viewModel.prevPage()
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.reset()
                viewModel.getByName(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    @SuppressLint("SetTextI18n")
    @Suppress("SENSELESS_COMPARISON")
    private fun initObserver(){
        viewModel.isFilter.observe(viewLifecycleOwner, {
            txt_reset.visibility = if (it) View.VISIBLE else View.INVISIBLE

            if(it){
                if(viewModel.options.value!!.containsKey("name")){
                    txt_reset.text = viewModel.options.value!!["name"] + "    X"
                }
                else if(viewModel.options.value!!.containsKey("status")){
                    txt_reset.text = viewModel.options.value!!["status"] + "    X"
                }
            }
        })

        viewModel.characters.observe(viewLifecycleOwner, {
            adapter.setCharacters(it)
            recyclerView.layoutManager!!.scrollToPosition(0)
            scrollView.scrollTo(0,0)
        })

        viewModel.pageInfo.observe(viewLifecycleOwner, {
            btn_prev.visibility = if (it.prev != null) View.VISIBLE else View.INVISIBLE
            btn_next.visibility = if (it.next != null) View.VISIBLE else View.INVISIBLE
        })

        viewModel.isDisplayModeGrid.observe(viewLifecycleOwner, {
            if(it){
                btn_display.setImageResource(R.drawable.ic_list)
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }else{
                btn_display.setImageResource(R.drawable.ic_grid)
                recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            }
        })
    }
}