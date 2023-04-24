package com.alessandrofarandagancio.anywhere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.alessandrofarandagancio.anywhere.repository.CharacterRepository
import com.alessandrofarandagancio.anywhere.ui.model.UICharacter
import com.alessandrofarandagancio.anywhere.ui.model.asViewModel
import com.alessandrofarandagancio.anywhere.ui.model.emptyUICharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    var characterMap = mutableMapOf<String, UICharacter>()

    val characterListResponse: LiveData<List<UICharacter>>
        get() = characterRepository.getAllCharacters().map {
            var map = it.asViewModel()
            map.forEach { characterMap.put(it.id, it) }
            map
        }

    fun getCharacterById(id: String): UICharacter {
        return characterMap.getOrDefault(id, emptyUICharacter())
    }

    fun refreshCharacter() {
        viewModelScope.launch {
            try {
                characterRepository.refreshCharacter()
            } catch (networkError: IOException) {

            }
        }
    }

}