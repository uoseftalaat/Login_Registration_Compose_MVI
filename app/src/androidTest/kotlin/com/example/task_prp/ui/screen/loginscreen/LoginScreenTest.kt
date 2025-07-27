package com.example.task_prp.ui.screen.loginscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {


    @get: Rule
    val composeRule = createComposeRule()


    @Test
    fun whenEnteringTextToThePhoneField_ThenTheValueShowsInThePhoneField(){
        composeRule.setContent {
            var phone by remember {
                mutableStateOf("")
            }
            LoginContent(
                state = LoginContract.LoginState(phoneNumber = phone)
            ) { intent ->
                when(intent){
                    is LoginContract.LoginIntent.OnPhoneNumberChange -> phone = intent.newPhoneNumber
                    else -> {}
                }
            }
        }
        val phoneText = "01012345678"
        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).assertTextEquals(phoneText)
    }

    @Test
    fun whenEnteringTextToThePasswordFieldAndClickingShowPassword_ThenTheValueShowsInThePasswordField(){
        composeRule.setContent {
            var password by remember {
                mutableStateOf("")
            }
            var isPasswordHidden by remember {
                mutableStateOf(true)
            }
            LoginContent(
                state = LoginContract.LoginState(password = password, isPasswordHidden = isPasswordHidden)
            ) { intent ->
                when(intent){
                    is LoginContract.LoginIntent.OnPasswordChange -> password = intent.newPassword
                    is LoginContract.LoginIntent.OnShowPasswordClick -> isPasswordHidden = !isPasswordHidden
                    else -> {}
                }
            }
        }
        val passwordText = "password"
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)
        composeRule.onNodeWithTag(LoginTestTags.SHOW_PASSWORD_BUTTON_TEST_TAG).performClick()
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).assertTextEquals(passwordText)
    }


    @Test
    fun whenEnteringPhoneAndPasswordIsEmpty_ThenButtonIsDisabled(){
        composeRule.setContent {
            var phone by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            var isPhoneEmpty by remember { mutableStateOf(true) }
            var isPasswordEmpty by remember { mutableStateOf(true) }
            LoginContent(
                state = LoginContract.LoginState(
                    phoneNumber = phone,
                    password = password,
                    isPhoneEmpty = isPhoneEmpty,
                    isPasswordEmpty = isPasswordEmpty
                )
            ){
                intent ->
                when(intent){
                    is LoginContract.LoginIntent.OnPhoneNumberChange -> {
                        isPhoneEmpty = intent.newPhoneNumber.isEmpty()
                        phone = intent.newPhoneNumber
                    }
                    is LoginContract.LoginIntent.OnPasswordChange -> {
                        isPasswordEmpty = intent.newPassword.isEmpty()
                        password = intent.newPassword
                    }
                    else -> {}
                }

            }
        }
        val phoneText = ""
        val passwordText = ""
        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)
        composeRule.onNodeWithTag(LoginTestTags.LOGIN_BUTTON_TEST_TAG).assertIsNotEnabled()
    }


    @Test
    fun whenEnteringPhoneAndPassword_ThenButtonIsEnabled(){
        composeRule.setContent {
            var phone by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            var isPhoneEmpty by remember { mutableStateOf(true) }
            var isPasswordEmpty by remember { mutableStateOf(true) }
            var isButtonEnabled by remember { mutableStateOf(false) }
            LoginContent(
                state = LoginContract.LoginState(
                    phoneNumber = phone,
                    password = password,
                    isPhoneEmpty = isPhoneEmpty,
                    isPasswordEmpty = isPasswordEmpty,
                    isProcessButtonEnabled = isButtonEnabled
                )
            ){ intent ->

                when(intent){
                    is LoginContract.LoginIntent.OnPhoneNumberChange -> {
                        isPhoneEmpty = intent.newPhoneNumber.isEmpty()
                        phone = intent.newPhoneNumber
                        isButtonEnabled = (!isPhoneEmpty && !isPasswordEmpty)
                    }
                    is LoginContract.LoginIntent.OnPasswordChange -> {
                        isPasswordEmpty = intent.newPassword.isEmpty()
                        password = intent.newPassword
                        isButtonEnabled = (!isPhoneEmpty && !isPasswordEmpty)
                    }
                    else -> {}
                }

            }
        }
        val phoneText = "0100"
        val passwordText = "password"
        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)
        composeRule.onNodeWithTag(LoginTestTags.LOGIN_BUTTON_TEST_TAG).assertIsEnabled()
    }

    @Test
    fun whenClickingCreateAccountButton_ThenCreateAccountIntentIsSent() {
        var receivedIntent: LoginContract.LoginIntent? = null

        composeRule.setContent {
            LoginContent(
                state = LoginContract.LoginState(
                    countryCode = "EG"
                ),
                setIntent = { intent -> receivedIntent = intent }
            )
        }

        composeRule.onNodeWithTag(LoginTestTags.CREATE_ACCOUNT_BUTTON_TEST_TAG)
            .performClick()

        assert(receivedIntent is LoginContract.LoginIntent.OnCreateAccountClick)
    }

    @Test
    fun whenClickingOnTheFlagIcon_ThenCountryPickerIntentIsSent() {
        var receivedIntent: LoginContract.LoginIntent? = null

        composeRule.setContent {
            LoginContent(
                state = LoginContract.LoginState(),
                setIntent = { intent -> receivedIntent = intent }
            )
        }

        composeRule.onNodeWithTag(LoginTestTags.FLAG_ICON_TEST_TAG).performClick()

        assert(receivedIntent is LoginContract.LoginIntent.OnCountryPickerClick)
    }

    @Test
    fun whenClickingOnTheLoginButton_ThenLoginIntentIsSent() {
        var receivedIntent: LoginContract.LoginIntent? = null

        composeRule.setContent {
            var phone by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            LoginContent(
                state = LoginContract.LoginState(
                    phoneNumber = phone,
                    password = password,
                    isPhoneValid = true,
                    isPasswordValid = true,
                    isProcessButtonEnabled = true
                ),
                setIntent = { intent ->
                    when(intent){
                        is LoginContract.LoginIntent.OnPhoneNumberChange -> phone = intent.newPhoneNumber
                        is LoginContract.LoginIntent.OnPasswordChange -> password = intent.newPassword
                        is LoginContract.LoginIntent.OnLoginClick -> receivedIntent = intent
                        else -> {}
                    }
                }
            )
        }
        val phoneText = "01012345473"
        val passwordText = "password"

        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)

        composeRule.onNodeWithTag(LoginTestTags.LOGIN_BUTTON_TEST_TAG).performClick()

        assertThat(receivedIntent).isInstanceOf(LoginContract.LoginIntent.OnLoginClick::class.java)
    }

    @Test
    fun whenClickingOnTheLoginButtonAndPasswordIsNotValid_ThenPasswordErrorIsShown() {
        composeRule.setContent {
            var state by remember {
                mutableStateOf(
                    LoginContract.LoginState(
                        phoneNumber = "",
                        password = "",
                        isPasswordValid = true,
                        isProcessButtonEnabled = true
                    )
                )
            }

            LoginContent(
                state = state,
                setIntent = { intent ->
                    when (intent) {
                        is LoginContract.LoginIntent.OnPhoneNumberChange -> {
                            state = state.copy(phoneNumber = intent.newPhoneNumber)
                        }

                        is LoginContract.LoginIntent.OnPasswordChange -> {
                            state = state.copy(password = intent.newPassword)
                        }

                        is LoginContract.LoginIntent.OnLoginClick -> {
                            val isValid = state.password.length >= 6
                            state = state.copy(
                                isPasswordValid = isValid,
                                passwordError = if (!isValid) "The minimum lenght for password is 6 characters" else ""
                            )
                        }

                        else -> {}
                    }
                }
            )
        }

        val phoneText = "01012345473"
        val passwordText = "pass"

        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)
        composeRule.onNodeWithTag(LoginTestTags.LOGIN_BUTTON_TEST_TAG).performClick()

        composeRule.onNodeWithText("The minimum lenght for password is 6 characters").assertExists()
    }

    @Test
    fun whenClickingOnTheLoginButtonAndPhoneIsNotValid_ThenPhoneErrorIsShown() {
        composeRule.setContent {
            var state by remember {
                mutableStateOf(
                    LoginContract.LoginState(
                        phoneNumber = "",
                        password = "",
                        isPhoneValid = true,
                        isProcessButtonEnabled = true
                    )
                )
            }

            LoginContent(
                state = state,
                setIntent = { intent ->
                    when (intent) {
                        is LoginContract.LoginIntent.OnPhoneNumberChange -> {
                            state = state.copy(phoneNumber = intent.newPhoneNumber)
                        }

                        is LoginContract.LoginIntent.OnPasswordChange -> {
                            state = state.copy(password = intent.newPassword)
                        }

                        is LoginContract.LoginIntent.OnLoginClick -> {
                            val isValid = false
                            state = state.copy(
                                isPhoneValid = isValid,
                                phoneError = if (!isValid) "phone Number not valid" else ""
                            )
                        }

                        else -> {}
                    }
                }
            )
        }

        val phoneText = "0101234"
        val passwordText = "password"

        composeRule.onNodeWithTag(LoginTestTags.PHONE_TEST_TAG).performTextInput(phoneText)
        composeRule.onNodeWithTag(LoginTestTags.PASSWORD_TEST_TAG).performTextInput(passwordText)
        composeRule.onNodeWithTag(LoginTestTags.LOGIN_BUTTON_TEST_TAG).performClick()

        composeRule.onNodeWithText("phone Number not valid").assertExists()
    }


}