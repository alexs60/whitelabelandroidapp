package com.alessandrofarandagancio.anywhere.api.duckduckgo.model

import com.google.gson.annotations.SerializedName


data class Character(

    @SerializedName("FirstURL")
    var firstURL: String,
    @SerializedName("Icon")
    var icon: Icon = emptyIcon(),
    @SerializedName("Text")
    var text: String

)