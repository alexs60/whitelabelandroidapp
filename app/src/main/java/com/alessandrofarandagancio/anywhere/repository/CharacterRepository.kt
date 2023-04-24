package com.alessandrofarandagancio.anywhere.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alessandrofarandagancio.anywhere.BuildConfig
import com.alessandrofarandagancio.anywhere.api.duckduckgo.ApiHelper
import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.Character
import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.CharacterResponse
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    private var _charactersList = MutableLiveData<List<Character>>()
    val charactersList: LiveData<List<Character>> get() = _charactersList

    fun getAllCharacters() = charactersList

    suspend fun refreshCharacter() {
        var character = apiHelper.getCharacter(BuildConfig.SEARCH_QUERY)
        if (character.isSuccessful) {
            val responseBody = character.body() as CharacterResponse
            responseBody.let {
                _charactersList.value = responseBody.characters
            }
        }
    }
}