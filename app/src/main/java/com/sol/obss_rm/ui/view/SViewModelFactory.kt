package com.sol.obss_rm.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.sol.obss_rm.api.Repository

@ExperimentalPagingApi
class SViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SViewModel(repository) as T
    }
}