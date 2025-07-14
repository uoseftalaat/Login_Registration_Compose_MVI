package com.example.task_prp.ui.screen

import kotlinx.serialization.Serializable


sealed class Navigation{
    @Serializable
    data class Login(
        val countryId:Int = 0
    )

    @Serializable
    data class SignUp(
        val countryId:Int = 0
    )

    @Serializable
    data class CountryPicker(
        val countryId: Int = 0
    )
}