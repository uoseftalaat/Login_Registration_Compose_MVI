package com.example.task_prp.ui.screen.countrypickerScreen

import androidx.lifecycle.SavedStateHandle
import com.example.task_prp.TestDispatcherExtension
import com.example.task_prp.domain.repository.CountryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import com.example.task_prp.domain.model.Country
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class, TestDispatcherExtension::class)
class CountryPickerViewModelTest {

    @Mock
    private lateinit var repository: CountryRepository

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var countryViewModel:CountryPickerViewModel

    @BeforeEach
    fun setup(){
        countryViewModel = CountryPickerViewModel(
            repository,
            savedStateHandle
        )
    }

    @Test
    fun `when user loads the screen then the countries state should show the countries`(){
        val countries = listOf(
            Country(),
            Country(),
            Country()
        )

        runBlocking {
            Mockito.`when`(repository.getAllCountries()).then {
                countries
            }
        }

        countryViewModel.getCountries("EG")

        assertThat(countryViewModel.currentState.countries).isEqualTo(
            countries
        )
    }

    @Test
    fun `when user clicks on country then selectedCountryCode should change correspondingly`(){
        val country = Country(
            "EG"
        )

        countryViewModel.setIntent(CountryPickerContract.CountryPickerIntent.OnCountryClick(countryCode = country.countryCode))

        assertThat(countryViewModel.currentState.selectedCountryCode).isEqualTo(
            country.countryCode
        )
    }
}