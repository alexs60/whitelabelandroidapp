package com.alessandrofarandagancio.anywhere.api.duckduckgo

import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(baseDuckDuckGoRestApiUrl)
    suspend fun getCharacter(
        @Query("format") format: String = "json",
        @Query("q") query: String
    ): Response<CharacterResponse>


}