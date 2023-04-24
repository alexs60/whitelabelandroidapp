package com.alessandrofarandagancio.anywhere.api.duckduckgo

import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.CharacterResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getCharacter(query: String): Response<CharacterResponse> {
        return apiService.getCharacter(query = query)
    }

}