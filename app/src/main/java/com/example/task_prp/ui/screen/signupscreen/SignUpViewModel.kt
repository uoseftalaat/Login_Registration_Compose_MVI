package com.example.task_prp.ui.screen.signupscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.task_prp.domain.repository.CountryRepository
import com.example.task_prp.domain.businessusecase.validator.EmailValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.NameValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.PhoneNumberValidatorUseCase
import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.screen.Navigation
import com.example.task_prp.ui.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: CountryRepository,
    savedStateHandle: SavedStateHandle,
    private val phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val nameValidatorUseCase: NameValidatorUseCase
):BaseViewModel<
        SignUpContract.SignupState,
        SignUpContract.SignUpIntent,
        SignUpContract.SignUpEffect>
    (){

    init {
        val countryCode = savedStateHandle.toRoute<Navigation.SignUp>().countryCode
        setState { copy(countryCode = countryCode) }
        subscribeIntents()
    }
    override fun createInitialState(): SignUpContract.SignupState {
        return SignUpContract.SignupState()
    }

    override fun handleIntent(intent: SignUpContract.SignUpIntent) {
        when(intent){
            is SignUpContract.SignUpIntent.OnBackButtonClick -> onBackButtonClick(intent.countryCode)
            is SignUpContract.SignUpIntent.OnConfirmClick -> validateAccountCreation()
            is SignUpContract.SignUpIntent.OnCountryPickClick -> onCountryPickClick(intent.countryCode)
            is SignUpContract.SignUpIntent.OnEmailChange -> onEmailChange(intent.newEmail)
            is SignUpContract.SignUpIntent.OnNameChange -> onNameChange(intent.newName)
            is SignUpContract.SignUpIntent.OnPhoneNumberChange -> onPhoneNumberChange(intent.newPhoneNumber)
            is SignUpContract.SignUpIntent.OnSurNameChange -> onSurNameChange(intent.newSurName)
            is SignUpContract.SignUpIntent.OnFlagChange -> onDefaultCountryChange(intent.countryCode)
        }
    }

    private fun onBackButtonClick(countryId: String) = setEffect {
        SignUpContract.SignUpEffect.OnBackButtonClick(countryId)
    }

    private fun validateAccountCreation() {
        validateNameSurName(currentState.name,currentState.surName)
        validatePhoneNumber(currentState.phoneNumber,currentState.country)
        validateEmail(currentState.email)

        if(currentState.isEmailValid == true &&
            currentState.isNameValid == true &&
            currentState.isSurNameValid == true &&
            currentState.isPhoneNumberValid == true
        )
            setEffect{
                SignUpContract.SignUpEffect.OnCreateAccountClick(currentState.country?.countryCode ?: "EG")
            }

    }

    private fun onCountryPickClick(countryCode: String) = setEffect{
        SignUpContract.SignUpEffect.OnCountryPickClick(countryCode)
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

    private fun onDefaultCountryChange(countryCode: String?) {
        val id = countryCode ?: currentState.countryCode
        getCountryById(countryCode ?: id ?: "EG")
    }

    private fun getCountryById(countryCode: String) = viewModelScope.launch {
        val country = repository.getCountryById(countryCode)
        setState { copy(country = country, countryCode = countryCode) }
    }

    private fun validateNameSurName(name: String, surName: String) {
        val nameResult = nameValidatorUseCase(name)
        val surNameResult = nameValidatorUseCase(surName)
        setState {
            copy(
                isNameValid = nameResult.isEntryValid,
                isSurNameValid = surNameResult.isEntryValid,
                nameError = if (!nameResult.isEntryValid) nameResult.errorMessage else "",
                surNameError = if (!nameResult.isEntryValid) surNameResult.errorMessage else "",
            )
        }
    }

    private fun validateEmail(email: String) {
        val result = emailValidatorUseCase(email)
        setState {
            copy(
                isEmailValid = result.isEntryValid,
                emailError = if(!result.isEntryValid) result.errorMessage else ""
            )
        }
    }

    private fun validatePhoneNumber(phoneNumber: String, country: Country?) {
        val results = phoneNumberValidatorUseCase(phoneNumber,country?.countryCode ?: "20")
        setState { copy(isPhoneNumberValid = results.isEntryValid, phoneError = results.errorMessage) }
    }
}