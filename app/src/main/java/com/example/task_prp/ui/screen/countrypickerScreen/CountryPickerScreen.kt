package com.example.task_prp.ui.screen.countrypickerScreen


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.task_prp.ui.common.AppCountryCode
import com.example.task_prp.ui.common.AppTitleField
import com.example.task_prp.ui.screen.loginscreen.LoginContract
import org.checkerframework.checker.units.qual.Current
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CountryPickerScreen(
    modifier: Modifier,
    onBackClicked: (String) -> Unit,
    onCountrySelected: (String) -> Unit
) {
    val viewModel = koinViewModel<CountryPickerViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit){
        viewModel.effect.collect{
            when(it){
                is CountryPickerContract.CountryPickerEffect.BackButtonClick -> onBackClicked(it.countryCode)
                is CountryPickerContract.CountryPickerEffect.CountrySelected -> onCountrySelected(state.value.selectedCountryCode)
            }
        }
    }
    ContentChooser(
        modifier,
        state.value,
        viewModel::setIntent
    )
}

@Composable
fun ContentChooser(
    modifier: Modifier,
    state: CountryPickerContract.CountryPickerState,
    setIntent: (CountryPickerContract.CountryPickerIntent) -> Unit,
    ) {
    if(!state.isLoading){
        CountryPickerContent(
            modifier,
            state,
            setIntent
        )
    }
    else{
        Box(
            modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
    }
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
            setIntent(CountryPickerContract.CountryPickerIntent.OnBackClick(state.selectedCountryCode))
        }

        LazyColumn {
            items(state.countries){ country ->
                AppCountryCode(country = country){
                    val countryId = country.countryCode
                    setIntent(CountryPickerContract.CountryPickerIntent.OnCountryClick(countryId))

                }
            }
        }
    }
}