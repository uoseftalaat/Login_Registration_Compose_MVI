package com.example.task_prp.ui.screen.countrypickerScreen

import com.example.task_prp.data.Country
import com.example.task_prp.ui.screen.base.UiEffect
import com.example.task_prp.ui.screen.base.UiIntent
import com.example.task_prp.ui.screen.base.UiState

class CountryPickerContract {

    sealed class CountryPickerIntent:UiIntent{
        data class OnBackClick(val countryId: Int):CountryPickerIntent()
        data class OnCountryClick(val countryId:Int):CountryPickerIntent()
    }

    data class CountryPickerState(
        val isLoading:Boolean = true,
        val countries:List<Country> = listOf(),
        val selectedCountryID:Int = 0
    ) :UiState

    sealed class CountryPickerEffect:UiEffect{
        data class BackButtonClick(val countryId:Int):CountryPickerEffect()
        data class CountrySelected(val countryID:Int):CountryPickerEffect()
    }
}