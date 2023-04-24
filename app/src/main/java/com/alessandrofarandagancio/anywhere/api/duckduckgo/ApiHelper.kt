package com.alessandrofarandagancio.anywhere.api.duckduckgo

import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.CharacterResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getCharacter(flavour: String): Response<CharacterResponse>
}