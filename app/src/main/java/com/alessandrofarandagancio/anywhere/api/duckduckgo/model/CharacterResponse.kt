package com.alessandrofarandagancio.anywhere.api.duckduckgo.model

import com.google.gson.annotations.SerializedName


data class CharacterResponse (

  @SerializedName("RelatedTopics" ) var characters : ArrayList<Character> = arrayListOf()

)