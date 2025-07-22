package com.example.task_prp.ui.screen.countrypickerScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.task_prp.domain.businessusecase.countryusecase.GetAllCountriesUseCase
import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.connectivity.ConnectivityObserver
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryPickerViewModel(
    private val getAllCountries: GetAllCountriesUseCase,
    private val connectionObserver:ConnectivityObserver,
    savedStateHandle: SavedStateHandle
):BaseViewModel<
        CountryPickerContract.CountryPickerState,
        CountryPickerContract.CountryPickerIntent,
        CountryPickerContract.CountryPickerEffect
        >()
{
    val id = savedStateHandle.toRoute<Navigation.CountryPicker>().countryCode
    init {

        getCountries(id)
        subscribeIntents()
        onConnectionStarts()
    }

    override fun handleIntent(intent: CountryPickerContract.CountryPickerIntent) {
        when(intent){
            is CountryPickerContract.CountryPickerIntent.OnBackClick -> onBackClick()
            is CountryPickerContract.CountryPickerIntent.OnCountryClick -> onCountryButtonClick(intent.countryCode)
        }
    }

    fun getCountries(selectedCountryCode:String){
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val countries = getAllCountries()
            countries.forEach {
                    country: Country ->  country.isPicked = country.countryCode == selectedCountryCode
            }
            setState { copy(countries = countries , isLoading = false) }
        }
    }

    override fun createInitialState(): CountryPickerContract.CountryPickerState {
        return CountryPickerContract.CountryPickerState()
    }

    private fun onBackClick() {
        currentState.countries.forEach { country: Country ->
            if(country.isPicked)
                setEffect {
                    CountryPickerContract.CountryPickerEffect.BackButtonClick(country.countryCode)
                }
        }
    }

    private fun onCountryButtonClick(countryCode:String){
        setState { copy(selectedCountryCode = countryCode) }
        setEffect {
            CountryPickerContract.CountryPickerEffect.CountrySelected(countryCode)
        }
    }

    private fun onConnectionStarts() = viewModelScope.launch(Dispatchers.IO) {
        connectionObserver.connectionObserver().collect {
            setState { copy(isInternetConnected = it == ConnectivityObserver.ConnectionStatus.Connected) }
            getCountries(id)
        }
    }
}
