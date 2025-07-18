package com.example.task_prp.ui.screen

import kotlinx.serialization.Serializable


sealed class Navigation{
    @Serializable
    data class Login(
        val countryCode:String = "EG"
    )

    @Serializable
    data class SignUp(
        val countryCode:String = "EG"
    )

    @Serializable
    data class CountryPicker(
        val countryCode:String = "EG"
    )
}