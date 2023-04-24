package com.alessandrofarandagancio.anywhere.ui.model

import com.alessandrofarandagancio.anywhere.api.duckduckgo.baseDuckDuckGoImageUrl
import com.alessandrofarandagancio.anywhere.api.duckduckgo.model.Character


data class UICharacter(
    var id: String,
    var name: String,
    var icon: String,
    var text: String
)

fun emptyUICharacter(): UICharacter {
    return UICharacter(id = "", name = "", icon = "", text = "")
}


fun List<Character>.asViewModel(): List<UICharacter> {
    return map {
        UICharacter(
            id = it.firstURL.lowercase(),
            name = it.text.takeWhile { char -> char != '-' },
            icon = if (it.icon.URL.isNotBlank()) "$baseDuckDuckGoImageUrl${it.icon.URL}" else "",
            text = it.text
        )
    }
}