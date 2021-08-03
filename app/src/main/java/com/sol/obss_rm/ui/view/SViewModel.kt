package com.sol.obss_rm.ui.view

import androidx.lifecycle.*
import androidx.paging.*
import com.sol.obss_rm.model.Episode
import com.sol.obss_rm.api.Repository
import com.sol.obss_rm.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class SViewModel(val repository: Repository = Repository.getInstance()) : ViewModel() {
    var filterValue = MutableLiveData<Array<Int>>()
    var isFilter = MutableLiveData<Boolean>()
    var lastEpisode = MutableLiveData<Episode>()
    lateinit var characters: Flow<PagingData<Character>>

    init {
        filterValue.value = arrayOf(0)
        isFilter.value = false
    }

    fun getCharacters(){
        val options: HashMap<String, String> = HashMap()
        viewModelScope.launch{
            try{
                characters = repository.getCharacters(options).cachedIn(viewModelScope)
            }catch (e:Exception){ }
        }
    }

    fun getByStatus(status : String){
        val options: HashMap<String, String> = HashMap()
        options["status"] = status

        viewModelScope.launch{
            try{
                characters = repository.getCharacters(options).cachedIn(viewModelScope)
                isFilter.value = true
            }catch(e: Exception){ }
        }
    }

    fun getByName(name: String){
        val options: HashMap<String, String> = HashMap()
        options["name"] = name

        viewModelScope.launch{
            try{
                characters = repository.getCharacters(options).cachedIn(viewModelScope)
                isFilter.value = true
            }catch(e: Exception){ }
        }
    }

    fun getEpisode(episode: Int) {
        viewModelScope.launch{
            try{
                lastEpisode.value = repository.getEpisode(episode)
            }catch(e: Exception){ }
        }
    }
}