package com.example.task_prp.ui.screen.loginscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.task_prp.domain.repository.CountryRepository
import com.example.task_prp.domain.businessusecase.PasswordValidatorUseCase
import com.example.task_prp.domain.businessusecase.PhoneNumberValidatorUseCase
import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.connectivity.ConnectivityObserver
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: CountryRepository,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase,
    savedStateHandle: SavedStateHandle,
): BaseViewModel<
        LoginContract.LoginState,
        LoginContract.LoginIntent,
        LoginContract.LoginEffect
        >()
{
    override fun createInitialState(): LoginContract.LoginState {
        return LoginContract.LoginState()
    }

    init {
        val countryId = savedStateHandle.toRoute<Navigation.Login>().countryCode
        setState { copy(countryCode = countryId) }
        subscribeIntents()
    }

    override fun handleIntent(intent: LoginContract.LoginIntent) {
        when(intent){
            is LoginContract.LoginIntent.OnPasswordChange -> onPasswordChange(intent.newPassword)

            is LoginContract.LoginIntent.OnPhoneNumberChange -> onPhoneNumberChange(intent.newPhoneNumber)

            LoginContract.LoginIntent.OnShowPasswordClick -> onPasswordVisibilityIconClick()

            is LoginContract.LoginIntent.OnCountryPickerClick -> onCountryPickerClick(intent.countryCode)

            LoginContract.LoginIntent.OnLoginClick -> onLoginButtonClick()

            is LoginContract.LoginIntent.OnDefaultCountryChange -> onDefaultCountryChange(intent.countryCode ?: currentState.countryCode)


            is LoginContract.LoginIntent.OnCreateAccountClick -> onCreateAccountClick(intent.countryCode)
        }
    }

    private fun onPasswordChange(newPassword:String) {
        setState {
            copy(password = newPassword, isPasswordEmpty = newPassword.isEmpty(), isProcessButtonEnabled = (newPassword.isNotEmpty() && !currentState.isPhoneEmpty))
        }
    }

    private fun onPhoneNumberChange(newPhoneNumber:String) {
        setState { copy(phoneNumber = newPhoneNumber,isPhoneEmpty = newPhoneNumber.isEmpty(), isProcessButtonEnabled = (newPhoneNumber.isNotEmpty() && !currentState.isPasswordEmpty) ) }
    }

    private fun onPasswordVisibilityIconClick() {
        setState { copy(isPasswordHidden = !isPasswordHidden) }
    }

    private fun onCountryPickerClick(countryId: String){
        setEffect {
            LoginContract.LoginEffect.CountryPickerClick(countryId)
        }
    }

    private fun onLoginButtonClick(){
        validatePhoneNumber(currentState.phoneNumber, currentState.country)
        validatePassword(currentState.password)
        if(currentState.isPhoneValid == true && currentState.isPasswordValid == true) {
            setEffect {
                LoginContract.LoginEffect.Login
            }
        }
    }

    private fun onDefaultCountryChange(countryCode:String?) = viewModelScope.launch {
            val country = repository.getCountryById(countryCode ?: "EG")
            setState { copy(country = country, countryCode = countryCode ?: "EG") }
    }


    private fun onCreateAccountClick(countryCode: String) = setEffect {
        LoginContract.LoginEffect.CreateAccount(countryCode)
    }


    private fun validatePhoneNumber(phoneNumber: String, country: Country?) {
        val results = phoneNumberValidatorUseCase(phoneNumber,country?.countryCode ?: "EG")
        setState { copy(isPhoneValid = results.isEntryValid, phoneError = results.errorMessage) }
    }

    private fun validatePassword(password:String){
        val result = passwordValidatorUseCase(password)
        setState { copy(isPasswordValid = result.isEntryValid, passwordError = result.errorMessage) }
    }

}