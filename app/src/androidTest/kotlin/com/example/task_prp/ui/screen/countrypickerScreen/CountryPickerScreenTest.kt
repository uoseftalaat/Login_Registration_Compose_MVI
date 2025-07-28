package com.example.task_prp.ui.screen.countrypickerScreen

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.screen.signupscreen.SignUpTestTag
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test


class CountryPickerScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenNavigatingToCountryPickerScreen_ThenLoadingIndicatorIsDisplayed(){
        composeTestRule.setContent {
            ContentChooser(
                modifier = Modifier,
                state = CountryPickerContract.CountryPickerState(
                    isLoading = true,
                    isInternetConnected = true
                )
            ) { }
        }

        composeTestRule.onNodeWithTag("loadingIndicator").assertExists()
    }

    @Test
    fun whenInternetIsNotConnected_ThenNoInternetViewIsDisplayed(){
        composeTestRule.setContent {
            ContentChooser(
                modifier = Modifier,
                state = CountryPickerContract.CountryPickerState(
                    isInternetConnected = false,
                )
            ) { }
        }

        composeTestRule.onNodeWithTag("NoInternetView").assertExists()
    }


    @Test
    fun whenClickingBack_ThenBackIntentIsFired(){
        var receivedIntent: CountryPickerContract.CountryPickerIntent.OnBackClick? = null
        composeTestRule.setContent {
            CountryPickerContent(
                modifier = Modifier,
                state = CountryPickerContract.CountryPickerState(
                )
            ) { intent ->
                when(intent){
                    is CountryPickerContract.CountryPickerIntent.OnBackClick -> {
                        receivedIntent = intent
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.BACK_BUTTON_TEST_TAG).performClick()
        assertThat(receivedIntent).isInstanceOf(CountryPickerContract.CountryPickerIntent.OnBackClick::class.java)

    }

    @Test
    fun whenClickingCountry_ThenCountryIntentIsFired(){
        var receivedIntent: CountryPickerContract.CountryPickerIntent.OnCountryClick? = null
        composeTestRule.setContent {
            CountryPickerContent(
                modifier = Modifier,
                state = CountryPickerContract.CountryPickerState(
                    countries = listOf(
                        Country(
                            "EG",
                            "20",
                            "EGYPT",
                            "\uD83C\uDDE6\uD83C\uDDEA"
                        )
                    )
                )
            ) { intent ->
                when(intent){
                    is CountryPickerContract.CountryPickerIntent.OnCountryClick -> {
                        receivedIntent = intent
                    }
                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag("countryClick").performClick()
        assertThat(receivedIntent).isInstanceOf(CountryPickerContract.CountryPickerIntent.OnCountryClick::class.java)

    }



}