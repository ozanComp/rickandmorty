package com.sol.obss_rm.ui.view

import androidx.lifecycle.*
import com.sol.obss_rm.model.Episode
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.model.PageInfo
import kotlinx.coroutines.launch

class SViewModel(val repository: Repository = Repository.getInstance()) : ViewModel() {
    var filterValue = MutableLiveData<Array<Int>>()
    var pageInfo =  MutableLiveData<PageInfo>()
    var isFilter = MutableLiveData<Boolean>()
    var lastEpisode = MutableLiveData<Episode>()
    var characters = MutableLiveData<List<Character>>()
    var isDisplayModeGrid = MutableLiveData<Boolean>()
    var options = MutableLiveData<HashMap<String, String>>()

    var currentPage:Int = 0

    init {
        currentPage = 1
        filterValue.value = arrayOf(0)
        isFilter.value = false
        isDisplayModeGrid.value = true
        options.value = HashMap()
    }

    fun reset(){
        currentPage = 1
        isFilter.value = false
        options.value!!.clear()
    }

    fun nextPage(){
        viewModelScope.launch{
            currentPage++

            getCharacters()
        }
    }

    fun prevPage(){
        viewModelScope.launch{
            if(currentPage != 0)
                currentPage--

            getCharacters()
        }
    }

    fun getCharacters(){
        options.value!!["page"] = "" + currentPage

        viewModelScope.launch{
            try{
                val char = repository.getCharacters(options.value!!)
                characters.value = char.results
                pageInfo.value = char.info
            }catch(e: Exception){ }
        }
    }

    fun getByStatus(status : String){
        options.value!!["status"] = "" + status
        getCharacters()

        isFilter.value = true
    }

    fun getByName(name: String){
        options.value!!["name"] = "" + name
        getCharacters()

        isFilter.value = true
    }

    fun getEpisode(episode: Int) {
        viewModelScope.launch{
            try{
                lastEpisode.value = repository.getEpisode(episode)
            }catch(e: Exception){ }
        }
    }
}