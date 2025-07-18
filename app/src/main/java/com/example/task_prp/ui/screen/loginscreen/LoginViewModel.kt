package com.example.task_prp.ui.screen.loginscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.task_prp.data.remote.Country
import com.example.task_prp.domain.repository.CountryRepository
import com.example.task_prp.domain.businessusecase.PasswordValidatorUseCase
import com.example.task_prp.domain.businessusecase.PhoneNumberValidatorUseCase
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: CountryRepository,
    savedStateHandle: SavedStateHandle,
    val passwordValidatorUseCase: PasswordValidatorUseCase,
    val phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase
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
        val countryId = savedStateHandle.toRoute<Navigation.Login>().countryId
        setState { copy(countryId = countryId) }
        subscribeIntents()
    }

    override fun handleIntent(intent: LoginContract.LoginIntent) {
        when(intent){
            is LoginContract.LoginIntent.OnPasswordChange -> onPasswordChange(intent.newPassword)

            is LoginContract.LoginIntent.OnPhoneNumberChange -> onPhoneNumberChange(intent.newPhoneNumber)

            LoginContract.LoginIntent.OnShowPasswordClick -> onPasswordVisibilityIconClick()

            is LoginContract.LoginIntent.OnCountryPickerClick -> onCountryPickerClick(intent.countryId)

            LoginContract.LoginIntent.OnLoginClick -> onLoginButtonClick()

            is LoginContract.LoginIntent.OnDefaultCountryChange -> onDefaultCountryChange(intent.countryId ?: currentState.countryId)


            is LoginContract.LoginIntent.OnCreateAccountClick -> onCreateAccountClick(intent.countryId)
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

    private fun onCountryPickerClick(countryId: Int){
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

    private fun onDefaultCountryChange(id:Int?) = viewModelScope.launch {
            val country = repository.getCountryById(id ?: 0)
            setState { copy(country = country, countryId = id ?: 0) }
    }


    private fun onCreateAccountClick(countryId: Int) = setEffect {
        LoginContract.LoginEffect.CreateAccount(countryId)
    }


    private fun validatePhoneNumber(phoneNumber: String, country: Country?) {
        val results = phoneNumberValidatorUseCase(phoneNumber,country?.countryCode ?: "20")
        setState { copy(isPhoneValid = results.isEntryValid, phoneError = results.errorMessage) }
    }

    private fun validatePassword(password:String){
        val result = passwordValidatorUseCase(password)
        setState { copy(isPasswordValid = result.isEntryValid, passwordError = result.errorMessage) }
    }

}