package com.sol.obss_rm.api

import com.sol.obss_rm.model.CharacterList
import com.sol.obss_rm.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface Api {
    @GET("api/character")
    suspend fun getCharacters(@QueryMap options: Map<String, String>): CharacterList

    @GET("api/episode/{episode}")
    suspend fun getEpisode(@Path("episode") episode : Int): Episode
}