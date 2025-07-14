package com.example.task_prp.ui.screen.signupscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.task_prp.data.Country
import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.domain.EmailValidatorUseCase
import com.example.task_prp.domain.NameValidatorUseCase
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: CountryRepository,
    private val savedStateHandle: SavedStateHandle,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val nameValidatorUseCase: NameValidatorUseCase
):BaseViewModel<
        SignUpContract.SignupState,
        SignUpContract.SignUpIntent,
        SignUpContract.SignUpEffect>
    (){

    init {
        val countryId = savedStateHandle.toRoute<Navigation.SignUp>().countryId
        setState { copy(countryId = countryId) }
        subscribeIntents()
    }
    override fun createInitialState(): SignUpContract.SignupState {
        return SignUpContract.SignupState()
    }

    override fun handleIntent(intent: SignUpContract.SignUpIntent) {
        when(intent){
            is SignUpContract.SignUpIntent.OnBackButtonClick -> onBackButtonClick(intent.countryId)
            is SignUpContract.SignUpIntent.OnConfirmClick -> validateAccountCreation()
            is SignUpContract.SignUpIntent.OnCountryPickClick -> onCountryPickClick(intent.countryId)
            is SignUpContract.SignUpIntent.OnEmailChange -> onEmailChange(intent.newEmail)
            is SignUpContract.SignUpIntent.OnNameChange -> onNameChange(intent.newName)
            is SignUpContract.SignUpIntent.OnPhoneNumberChange -> onPhoneNumberChange(intent.newPhoneNumber)
            is SignUpContract.SignUpIntent.OnSurNameChange -> onSurNameChange(intent.newSurName)
            is SignUpContract.SignUpIntent.OnFlagChange -> onDefaultCountryChange(intent.countryId)
        }
    }

    private fun onBackButtonClick(countryId: Int) = setEffect {
        SignUpContract.SignUpEffect.OnBackButtonClick(countryId)
    }

    private fun validateAccountCreation() {
        validateNameSurName(currentState.name,currentState.surName)
        validatePhoneNumber(currentState.phoneNumber)
        validateEmail(currentState.email)

        if(currentState.isEmailValid == true &&
            currentState.isNameValid == true &&
            currentState.isSurNameValid == true &&
            currentState.isPhoneNumberValid == true
        )
            setEffect{
                SignUpContract.SignUpEffect.OnCreateAccountClick(currentState.country?.id ?: 0)
            }

    }

    private fun onCountryPickClick(countryId: Int) = setEffect{
        SignUpContract.SignUpEffect.OnCountryPickClick(countryId)
    }

    private fun onEmailChange(newEmail:String) {
        setState {
            copy(
                email = newEmail,
                isEmailEmpty = newEmail.isEmpty(),
                isCreateAccountButtonEnabled = (
                        newEmail.isNotEmpty() &&
                                !currentState.isNameEmpty &&
                                !currentState.isSurNameEmpty &&
                                !currentState.isPhoneNumberEmpty
                        )
            )
        }
    }

    private fun onNameChange(newName:String) {
        setState {
            copy(
                name = newName,
                isNameEmpty = newName.isEmpty(),
                isCreateAccountButtonEnabled =(
                        newName.isNotEmpty() &&
                                !currentState.isEmailEmpty &&
                                !currentState.isSurNameEmpty &&
                                !currentState.isPhoneNumberEmpty
                        )
            )
        }
    }

    private fun onPhoneNumberChange(newPhoneNumber:String){
        setState {
            copy(
                phoneNumber = newPhoneNumber,
                isPhoneNumberEmpty = newPhoneNumber.isEmpty(),
                isCreateAccountButtonEnabled =(
                        newPhoneNumber.isNotEmpty() &&
                                !currentState.isEmailEmpty &&
                                !currentState.isSurNameEmpty &&
                                !currentState.isNameEmpty
                        )
            )
        }
    }

    private fun onSurNameChange(newSurName:String){
        setState {
            copy(
                surName = newSurName,
                isSurNameEmpty = newSurName.isEmpty(),
                isCreateAccountButtonEnabled =(
                        newSurName.isNotEmpty() &&
                                !currentState.isEmailEmpty &&
                                !currentState.isNameEmpty &&
                                !currentState.isPhoneNumberEmpty
                        )
            )
        }
    }

    private fun onDefaultCountryChange(countryId: Int?) {
        val id = countryId ?: currentState.countryId
        getCountryById(id ?: 0)
    }

    private fun getCountryById(countryId: Int) = viewModelScope.launch {
        val country = repository.getCountryById(countryId)
        setState { copy(country = country) }
    }

    private fun validateNameSurName(name: String, surName: String) {
        val nameValid = nameValidatorUseCase(name)
        val surNameValid = nameValidatorUseCase(surName)
        setState {
            copy(
                isNameValid = nameValid,
                isSurNameValid = surNameValid,
                nameError = if (!nameValid) "Name Must have at least 2 letters" else "",
                surNameError = if (!surNameValid) "SurName Must have at least 2 letters" else "",
            )
        }
    }

    private fun validateEmail(email: String) {
        val isValid = emailValidatorUseCase(email)
        setState {
            copy(
                isEmailValid = isValid,
                emailError = if(!isValid) "Please Enter a Valid Email Format" else ""
            )
        }
    }

    private fun validatePhoneNumber(phoneNumber: String) {
        val country = currentState.country
        val phoneNumberUtil = PhoneNumberUtil.getInstance()

        try {
            val regionCode = phoneNumberUtil.getRegionCodeForCountryCode(country?.countryCode?.toInt() ?: 20)

            val parsedNumber = phoneNumberUtil.parse(phoneNumber, regionCode)
            if (phoneNumberUtil.isValidNumberForRegion(parsedNumber, regionCode)) {
                setState { copy(isPhoneNumberValid = true, phoneError = "") }
            } else {
                setState { copy(isPhoneNumberValid = false, phoneError = "Phone number is not in a valid format") }
            }
        } catch (e: NumberParseException) {
            setState { copy(isPhoneNumberValid = false,phoneError = "Please enter numbers only") }
        }
    }
}