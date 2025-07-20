package com.example.task_prp.ui.screen.signupscreen

import androidx.lifecycle.SavedStateHandle
import com.example.task_prp.TestDispatcherExtension
import com.example.task_prp.domain.businessusecase.countryusecase.GetCountryByCountryCode
import com.example.task_prp.domain.repository.CountryRepository
import com.example.task_prp.domain.businessusecase.validator.EmailValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.NameValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.PhoneNumberValidatorUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import com.example.task_prp.domain.businessusecase.validator.UseCaseResult
import com.example.task_prp.domain.model.Country
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class, TestDispatcherExtension::class)
class SignUpViewModelTest {

    @Mock
    private lateinit var getCountryByCountryCode: GetCountryByCountryCode

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var nameValidatorUseCase: NameValidatorUseCase

    @Mock
    private lateinit var emailValidatorUseCase: EmailValidatorUseCase

    @Mock
    private lateinit var phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase

    private lateinit var signUpViewModel: SignUpViewModel

    @BeforeEach
    fun setup(){
        signUpViewModel = SignUpViewModel(
            getCountryByCountryCode,
            phoneNumberValidatorUseCase,
            emailValidatorUseCase,
            nameValidatorUseCase,
            savedStateHandle
        )
    }

    @Test
    fun `when user enters name to the name field then isNameEmpty = false`(){
        val name = "u"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))

        assertThat(signUpViewModel.currentState.isNameEmpty).isFalse()
    }

    @Test
    fun `when user enters name to the name field then name state should change correspondingly`(){
        val name = "Uosef"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))

        assertThat(signUpViewModel.currentState.name).isEqualTo(name)
    }

    @Test
    fun `when user enters surName to the surName field then isSurNameEmpty = false`(){
        val surName = "Talaat"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))

        assertThat(signUpViewModel.currentState.isSurNameEmpty).isFalse()
    }

    @Test
    fun `when user enters surName to the surName field then surName state should change correspondingly`(){
        val surName = "Talaat"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))

        assertThat(signUpViewModel.currentState.surName).isEqualTo(
            surName
        )
    }

    @Test
    fun `when user enters email to the email field then isEmailEmpty = false`(){
        val email = "uoseftalaat@gmail.com"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))

        assertThat(signUpViewModel.currentState.isEmailEmpty).isFalse()
    }

    @Test
    fun `when user enters email to the email field then email state should change correspondingly`(){
        val email = "uoseftalaat@gmail.com"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))

        assertThat(signUpViewModel.currentState.email).isEqualTo(
            email
        )
    }

    @Test
    fun `when user enters phone number to the phone number field then isPhoneNumber = false`(){
        val phoneNumber = "010"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(signUpViewModel.currentState.isPhoneNumberEmpty).isFalse()
    }

    @Test
    fun `when user enters phone number to the phone number field then phone number state should change correspondingly`(){
        val phoneNumber = "0104302"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(signUpViewModel.currentState.phoneNumber).isEqualTo(
            phoneNumber
        )
    }

    @Test
    fun `when user enters all the field without empty fields then isCreateAccountButtonEnabled = true`(){
        val name = "Uosef"
        val surName = "Talaat"
        val email = "UosefTalaat@gmail.com"
        val phoneNumber = "01063935766"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(signUpViewModel.currentState.isCreateAccountButtonEnabled).isTrue()
    }

    @Test
    fun `when user enters all the field with null name then isCreateAccountButtonEnabled = false`(){
        val name = ""
        val surName = "Talaat"
        val email = "UosefTalaat@gmail.com"
        val phoneNumber = "01063935766"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(signUpViewModel.currentState.isCreateAccountButtonEnabled).isFalse()
    }

    @Test
    fun `when user enters all the field with null email then isCreateAccountButtonEnabled = false`(){
        val name = "Uosef"
        val surName = "Talaat"
        val email = ""
        val phoneNumber = "01063935766"

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(signUpViewModel.currentState.isCreateAccountButtonEnabled).isFalse()
    }

    @Test
    fun `when country change then country state should change correspondingly`(){
        val country = Country(

        )

        runBlocking {
            Mockito.`when`(getCountryByCountryCode(Mockito.anyString())).then {
                country
            }
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnFlagChange(country.countryCode))

        assertThat(signUpViewModel.currentState.country).isEqualTo(
            country
        )
    }

    @Test
    fun `when country change then countryId state should change correspondingly`(){
        val country = Country(
            "EG"
        )

        runBlocking {
            Mockito.`when`(getCountryByCountryCode(Mockito.anyString())).then {
                country
            }
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnFlagChange(country.countryCode))

        assertThat(signUpViewModel.currentState.countryCode).isEqualTo(
            country.countryCode
        )
    }

    @Test
    fun `when enters name and the name is valid then isNameValid = true`(){
        val name = "uosef"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.isNameValid).isTrue()
    }

    @Test
    fun `when enters name and the name is invalid then nameError should has error message `(){
        val name = "uosef"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                false,
                "errorMessage"
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.nameError).isEqualTo("errorMessage")
    }

    @Test
    fun `when enters invalid email then emailError should has error message `(){
        val email = "uosef"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.emailError).isEqualTo("error")
    }

    @Test
    fun `when enters invalid email then isEmailValid = false `(){
        val email = "uosef"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.isEmailValid).isFalse()
    }

    @Test
    fun `when enters invalid phoneNumber then isPhoneNumberValid = false `(){
        val phoneNumber = "0100"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(phoneNumber))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.isPhoneNumberValid).isFalse()
    }

    @Test
    fun `when enters invalid phoneNumber then phoneError should has error message`(){
        val phoneNumber = "0100"

        Mockito.`when`(nameValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(emailValidatorUseCase(Mockito.anyString())).then {
            UseCaseResult(
                true,
                ""
            )
        }
        Mockito.`when`(phoneNumberValidatorUseCase(Mockito.anyString(),Mockito.anyString())).then {
            UseCaseResult(
                false,
                "error"
            )
        }

        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnEmailChange(phoneNumber))
        signUpViewModel.setIntent(SignUpContract.SignUpIntent.OnConfirmClick)

        assertThat(signUpViewModel.currentState.phoneError).isEqualTo("error")
    }
}