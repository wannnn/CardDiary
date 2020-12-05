package com.claire.carddiary.data.model

data class ProfileItemData(
    val icon: String = "",
    val text: String = ""
) {
    companion object {
        val DEFAULT = listOf(
            ProfileItemData(text = "設定"),
            ProfileItemData(text = "設定"),
            ProfileItemData(text = "設定"),
            ProfileItemData(text = "設定"),
            ProfileItemData(text = "設定"),
        )
    }
}