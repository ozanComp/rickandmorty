package com.sol.obss_rm.api

import androidx.paging.*
import com.sol.obss_rm.model.Character
import com.sol.obss_rm.model.Episode
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class Repository (val api: Api = RetrofitInstance.api) {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = Repository()
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    fun getCharacters(options: HashMap<String, String>): Flow<PagingData<Character>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { CharacterPagingSource(api, options)} ).flow
    }

    suspend fun getEpisode(episode: Int): Episode {
        return api.getEpisode(episode)
    }
}