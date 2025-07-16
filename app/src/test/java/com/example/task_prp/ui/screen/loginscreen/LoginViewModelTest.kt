package com.example.task_prp.ui.screen.loginscreen

import androidx.lifecycle.SavedStateHandle
import com.example.task_prp.TestDispatcherExtension
import com.example.task_prp.data.Country
import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.domain.PasswordValidatorUseCase
import com.example.task_prp.domain.PhoneNumberValidatorUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import com.example.task_prp.R
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class, TestDispatcherExtension::class)
class LoginViewModelTest {

    @Mock
    private lateinit var repository: CountryRepository

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var passwordValidatorUseCase: PasswordValidatorUseCase

    @Mock
    private lateinit var phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase

    private lateinit var loginViewModel: LoginViewModel

    @BeforeEach
    fun setup(){
        loginViewModel = LoginViewModel(
            repository,
            savedStateHandle,
            passwordValidatorUseCase,
            phoneNumberValidatorUseCase
        )
    }

    @Test
    fun `when user enters password with only one letter then return isPasswordEmpty = false`(){
        val password = "t"
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPasswordChange(password))

        assertThat(loginViewModel.currentState.isPasswordEmpty).isFalse()
    }

    @Test
    fun `when user enters password with letters then state should change correspondingly`(){
        val password = "the"
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPasswordChange(password))

        assertThat(loginViewModel.currentState.password).isEqualTo(password)
    }

    @Test
    fun `when user enters incomplete phone number then isPhoneNumberEmpty = false`(){
        val phoneNumber = "01037"
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(loginViewModel.currentState.isPhoneEmpty).isFalse()
    }

    @Test
    fun `when user enters phone number then state should change correspondingly`(){
        val phoneNumber = "437580"
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))

        assertThat(loginViewModel.currentState.phoneNumber).isEqualTo(phoneNumber)
    }

    @Test
    fun `when user enters phone number and password -they are not empty- then isButtonEnabled is true`(){
        val phoneNumber = "01063"
        val password = "pass"

        loginViewModel.setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPasswordChange(password))

        assertThat(loginViewModel.currentState.isProcessButtonEnabled).isTrue()
    }

    @Test
    fun `when user enters phone number and password -phone is empty- then isButtonEnabled is false`(){
        val phoneNumber = ""
        val password = "pass"

        loginViewModel.setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPasswordChange(password))

        assertThat(loginViewModel.currentState.isProcessButtonEnabled).isFalse()
    }

    @Test
    fun `when user enters phone number and password -password is empty- then isButtonEnabled is false`(){
        val phoneNumber = "01063"
        val password = ""

        loginViewModel.setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))
        loginViewModel.setIntent(LoginContract.LoginIntent.OnPasswordChange(password))

        assertThat(loginViewModel.currentState.isProcessButtonEnabled).isFalse()
    }

    @Test
    fun `when user changes country from country picker then country state showed be changed correspondingly`(){
        val country = Country(0,R.drawable.egyptflag,"20","Egypt")

        runBlocking {
            Mockito.`when`(repository.getCountryById(Mockito.anyInt())).then {
                country
            }
        }

        loginViewModel.setIntent(LoginContract.LoginIntent.OnDefaultCountryChange(country.id))

        runBlocking {
            Mockito.verify(repository).getCountryById(country.id)
        }

        assertThat(loginViewModel.currentState.country).isEqualTo(
            country
        )

    }

    @Test
    fun `when user changes country from country picker then countryId state showed be changed correspondingly`(){
        val country = Country(0,R.drawable.egyptflag,"20","Egypt")

        runBlocking {
            Mockito.`when`(repository.getCountryById(Mockito.anyInt())).then {
                country
            }
        }

        loginViewModel.setIntent(LoginContract.LoginIntent.OnDefaultCountryChange(country.id))

        runBlocking {
            Mockito.verify(repository).getCountryById(country.id)
        }

        assertThat(loginViewModel.currentState.countryId).isEqualTo(
            country.id
        )
    }
}