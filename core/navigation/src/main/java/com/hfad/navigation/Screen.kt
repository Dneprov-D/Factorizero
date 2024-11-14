package com.hfad.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object MainMasterScreen: Screen()

}