package com.alessandrofarandagancio.anywhere.api.duckduckgo.model

import com.google.gson.annotations.SerializedName


data class Icon(

    @SerializedName("Height")
    var height: String,
    @SerializedName("URL")
    var URL: String,
    @SerializedName("Width")
    var width: String

)

fun emptyIcon(): Icon {
    return Icon(height = "", URL = "", width = "")
}
