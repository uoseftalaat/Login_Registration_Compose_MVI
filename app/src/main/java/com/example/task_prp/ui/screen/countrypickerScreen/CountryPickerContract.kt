package com.example.task_prp.ui.screen.countrypickerScreen

import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.screen.base.UiEffect
import com.example.task_prp.ui.screen.base.UiIntent
import com.example.task_prp.ui.screen.base.UiState

class CountryPickerContract {

    sealed class CountryPickerIntent:UiIntent{
        data class OnBackClick(val countryCode: String):CountryPickerIntent()
        data class OnCountryClick(val countryCode:String):CountryPickerIntent()
    }

    data class CountryPickerState(
        val isLoading:Boolean = false,
        val countries:List<Country> = listOf(),
        val selectedCountryCode:String = "",
        val isInternetConnected:Boolean? = null
    ) :UiState

    sealed class CountryPickerEffect:UiEffect{
        data class BackButtonClick(val countryCode:String):CountryPickerEffect()
        data class CountrySelected(val countryCode:String):CountryPickerEffect()
    }
}