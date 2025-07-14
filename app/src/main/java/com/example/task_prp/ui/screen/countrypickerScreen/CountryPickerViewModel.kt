package com.example.task_prp.ui.screen.countrypickerScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.task_prp.data.Country
import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class CountryPickerViewModel(
    private val repository: CountryRepository,
    private val savedStateHandle: SavedStateHandle
):BaseViewModel<
        CountryPickerContract.CountryPickerState,
        CountryPickerContract.CountryPickerIntent,
        CountryPickerContract.CountryPickerEffect
        >()
{

    init {
        val id = savedStateHandle.toRoute<Navigation.CountryPicker>().countryId
        getCountries(id)
        subscribeIntents()
    }

    private fun getCountries(selectedCountryId:Int){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val countries = repository.getAllCountries()
            countries.forEach {
                    country: Country ->  country.isPicked = country.id == selectedCountryId
            }
            setState { copy(countries = countries , isLoading = false) }
        }
    }

    override fun createInitialState(): CountryPickerContract.CountryPickerState {
        return CountryPickerContract.CountryPickerState()
    }

    override fun handleIntent(intent: CountryPickerContract.CountryPickerIntent) {
        when(intent){
            is CountryPickerContract.CountryPickerIntent.OnBackClick -> onBackClick()
            is CountryPickerContract.CountryPickerIntent.OnCountryClick -> onCountryButtonClick(intent.countryId)
        }
    }

    private fun onBackClick() {
        currentState.countries.forEach { country: Country ->
            if(country.isPicked)
                setEffect {
                    CountryPickerContract.CountryPickerEffect.BackButtonClick(country.id)
                }
        }

    }

    private fun onCountryButtonClick(countryId:Int){
        setState { copy(selectedCountryID = countryId) }
        setEffect {
            CountryPickerContract.CountryPickerEffect.CountrySelected(countryId)
        }
    }

}
