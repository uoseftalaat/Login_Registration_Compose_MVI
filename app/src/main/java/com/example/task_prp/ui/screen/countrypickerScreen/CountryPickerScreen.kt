package com.example.task_prp.ui.screen.countrypickerScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.task_prp.ui.common.AppCountryCode
import com.example.task_prp.ui.common.AppTitleField
import com.example.task_prp.ui.screen.loginscreen.LoginContract
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CountryPickerScreen(
    modifier: Modifier = Modifier,
    onBackClicked: (Int) -> Unit,
    onCountrySelected: (Int) -> Unit
) {

    val viewModel = koinViewModel<CountryPickerViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit){
        viewModel.effect.collect{
            when(it){
                is CountryPickerContract.CountryPickerEffect.BackButtonClick -> onBackClicked(it.countryId)
                is CountryPickerContract.CountryPickerEffect.CountrySelected -> onCountrySelected(state.value.selectedCountryID)
            }
        }
    }
    CountryPickerContent(
        modifier,
        state.value,
        viewModel::setIntent
    )
}



@Composable
fun CountryPickerContent(
    modifier: Modifier,
    state:CountryPickerContract.CountryPickerState,
    setIntent: (CountryPickerContract.CountryPickerIntent) -> Unit,
) {
    Column(
        modifier
    ) {
        Spacer(
            Modifier.size(16.dp)
        )
        AppTitleField("Select Country"){
            setIntent(CountryPickerContract.CountryPickerIntent.OnBackClick(state.selectedCountryID))
        }

        LazyColumn {
            items(state.countries){ country ->
                AppCountryCode(country = country){
                    val countryId = country.id
                    setIntent(CountryPickerContract.CountryPickerIntent.OnCountryClick(countryId))

                }
            }
        }
    }
}