package com.sol.obss_rm.api

import com.sol.obss_rm.model.CharacterList
import com.sol.obss_rm.model.Episode

class Repository (val api: Api = RetrofitInstance.api) {
    companion object {
        fun getInstance() = Repository()
    }

    suspend fun getCharacters(options: HashMap<String, String>): CharacterList {
        return api.getCharacters(options)
    }

    suspend fun getEpisode(episode: Int): Episode {
        return api.getEpisode(episode)
    }
}