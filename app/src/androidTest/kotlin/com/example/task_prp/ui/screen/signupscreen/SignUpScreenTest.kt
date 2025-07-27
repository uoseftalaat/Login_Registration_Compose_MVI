package com.example.task_prp.ui.screen.signupscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task_prp.ui.screen.loginscreen.LoginTestTags
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun whenEnteringNameToTheNameField_ThenNameFieldShouldChangeItsValue(){
        composeTestRule.setContent {
            var name by remember {
                mutableStateOf("")
            }
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(name = name)
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnNameChange -> {
                        name = intent.newName
                    }
                    else -> {}
                }
            }
        }
        val name = "Uosef"
        val nameField = composeTestRule.onNodeWithTag(SignUpTestTag.NAME_TEST_TAG)
        nameField.performTextInput(name)
        nameField.assertTextEquals(name)
    }

    @Test
    fun whenEnteringSurNameToTheSurNameField_ThenSurNameFieldShouldChangeItsValue(){
        composeTestRule.setContent {
            var surName by remember {
                mutableStateOf("")
            }
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(surName = surName)
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnSurNameChange -> {
                        surName = intent.newSurName
                    }
                    else -> {}
                }
            }
        }
        val surName = "Talaat"
        val nameField = composeTestRule.onNodeWithTag(SignUpTestTag.SUR_NAME_TEST_TAG)
        nameField.performTextInput(surName)
        nameField.assertTextEquals(surName)
    }

    @Test
    fun whenEnteringEmailToTheEmailField_ThenEmailFieldShouldChangeItsValue(){
        composeTestRule.setContent {
            var email by remember {
                mutableStateOf("")
            }
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(email = email)
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnEmailChange -> {
                        email = intent.newEmail
                    }
                    else -> {}
                }
            }
        }
        val email = "email@gmail.com"
        val nameField = composeTestRule.onNodeWithTag(SignUpTestTag.EMAIL_TEST_TAG)
        nameField.performTextInput(email)
        nameField.assertTextEquals(email)
    }

    @Test
    fun whenEnteringPhoneToThePhoneField_ThenPhoneFieldShouldChangeItsValue(){
        composeTestRule.setContent {
            var phone by remember {
                mutableStateOf("")
            }
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(phoneNumber = phone)
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnPhoneNumberChange -> {
                        phone = intent.newPhoneNumber
                    }
                    else -> {}
                }
            }
        }
        val phone = "01063"
        val nameField = composeTestRule.onNodeWithTag(SignUpTestTag.PHONE_TEST_TAG)
        nameField.performTextInput(phone)
        nameField.assertTextEquals(phone)
    }

    @Test
    fun whenClickingOnBackIcon_ThenBackIntentIsSent(){
        var receivedIntent: SignUpContract.SignUpIntent? = null
        composeTestRule.setContent {
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(),
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnBackButtonClick -> {
                        receivedIntent = intent
                    }
                    else -> {}
                }
            }
        }
        composeTestRule.onNodeWithTag(SignUpTestTag.BACK_BUTTON_TEST_TAG).performClick()
        assertThat(receivedIntent).isInstanceOf(SignUpContract.SignUpIntent.OnBackButtonClick::class.java)
    }

    @Test
    fun whenClickingOnPickFlag_ThenPickFlagIntentIsSent(){
        var receivedIntent: SignUpContract.SignUpIntent? = null
        composeTestRule.setContent {
            SignUpContent(
                Modifier,
                SignUpContract.SignupState(),
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnCountryPickClick -> {
                        receivedIntent = intent
                    }
                    else -> {}
                }
            }
        }
        composeTestRule.onNodeWithTag(LoginTestTags.FLAG_ICON_TEST_TAG).performClick()
        assertThat(receivedIntent).isInstanceOf(SignUpContract.SignUpIntent.OnCountryPickClick::class.java)
    }

    @Test
    fun whenTextFieldsValuesEmpty_thenConfirmButtonIsDisabled(){
        composeTestRule.setContent {
            var isNameEmpty by remember { mutableStateOf(false) }
            var isSurNameEmpty by remember { mutableStateOf(false) }
            var isEmailEmpty by remember { mutableStateOf(false) }
            var isPhoneEmpty by remember { mutableStateOf(false) }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isNameEmpty = isNameEmpty,
                    isSurNameEmpty = isSurNameEmpty,
                    isEmailEmpty = isEmailEmpty,
                    isPhoneNumberEmpty = isPhoneEmpty
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnNameChange -> {
                        isNameEmpty = true
                    }

                    is SignUpContract.SignUpIntent.OnSurNameChange ->{
                        isSurNameEmpty = true
                    }

                    is SignUpContract.SignUpIntent.OnEmailChange -> {
                        isEmailEmpty = true
                    }

                    is SignUpContract.SignUpIntent.OnPhoneNumberChange -> {
                        isPhoneEmpty = true
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.NAME_TEST_TAG).performTextInput("")
        composeTestRule.onNodeWithTag(SignUpTestTag.SUR_NAME_TEST_TAG).performTextInput("")
        composeTestRule.onNodeWithTag(SignUpTestTag.EMAIL_TEST_TAG).performTextInput("")
        composeTestRule.onNodeWithTag(SignUpTestTag.PHONE_TEST_TAG).performTextInput("")

        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).assertIsNotEnabled()
    }


    @Test
    fun whenTextFieldsHasValues_thenConfirmButtonIsEnabled(){
        composeTestRule.setContent {
            var isNameEmpty by remember { mutableStateOf(true) }
            var isSurNameEmpty by remember { mutableStateOf(true) }
            var isEmailEmpty by remember { mutableStateOf(true) }
            var isPhoneEmpty by remember { mutableStateOf(true) }
            var isButtonEnabled by remember { mutableStateOf(false) }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isNameEmpty = isNameEmpty,
                    isSurNameEmpty = isSurNameEmpty,
                    isEmailEmpty = isEmailEmpty,
                    isPhoneNumberEmpty = isPhoneEmpty,
                    isCreateAccountButtonEnabled = isButtonEnabled
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnNameChange -> {
                        isNameEmpty = false
                        isButtonEnabled = isEnableButton(isNameEmpty,isSurNameEmpty,isEmailEmpty,isPhoneEmpty)
                    }

                    is SignUpContract.SignUpIntent.OnSurNameChange ->{
                        isSurNameEmpty = false
                        isButtonEnabled = isEnableButton(isNameEmpty,isSurNameEmpty,isEmailEmpty,isPhoneEmpty)
                    }

                    is SignUpContract.SignUpIntent.OnEmailChange -> {
                        isEmailEmpty = false
                        isButtonEnabled = isEnableButton(isNameEmpty,isSurNameEmpty,isEmailEmpty,isPhoneEmpty)
                    }

                    is SignUpContract.SignUpIntent.OnPhoneNumberChange -> {
                        isPhoneEmpty = false
                        isButtonEnabled = isEnableButton(isNameEmpty,isSurNameEmpty,isEmailEmpty,isPhoneEmpty)
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.NAME_TEST_TAG).performTextInput("Ali")
        composeTestRule.onNodeWithTag(SignUpTestTag.SUR_NAME_TEST_TAG).performTextInput("Gaber")
        composeTestRule.onNodeWithTag(SignUpTestTag.EMAIL_TEST_TAG).performTextInput("AliGaber@aol.com")
        composeTestRule.onNodeWithTag(SignUpTestTag.PHONE_TEST_TAG).performTextInput("0100000")

        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).assertIsEnabled()
    }

    fun isEnableButton(isNameEmpty: Boolean, isSurNameEmpty: Boolean, isEmailEmpty: Boolean, isPhoneEmpty: Boolean): Boolean{
        return !(isNameEmpty || isSurNameEmpty || isEmailEmpty || isPhoneEmpty)
    }

    @Test
    fun whenNameIsNotValid_ThenNameErrorShows(){
        composeTestRule.setContent {
            var nameError by remember { mutableStateOf("") }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isCreateAccountButtonEnabled = true,
                    isNameValid = false,
                    nameError = nameError
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnNameChange -> {
                        nameError = "name is not valid"
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.NAME_TEST_TAG).performTextInput("J")
        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).performClick()
        composeTestRule.onNodeWithText("name is not valid").assertExists()
    }

    @Test
    fun whenSurNameIsNotValid_ThenSurNameErrorShows(){
        composeTestRule.setContent {
            var surNameError by remember { mutableStateOf("") }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isCreateAccountButtonEnabled = true,
                    isSurNameValid = false,
                    surNameError = surNameError
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnSurNameChange -> {
                        surNameError =  "sur name is not valid"
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.SUR_NAME_TEST_TAG).performTextInput("J")
        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).performClick()
        composeTestRule.onNodeWithText("sur name is not valid").assertExists()
    }

    @Test
    fun whenEmailIsNotValid_ThenEmailErrorShows(){
        composeTestRule.setContent {
            var emailError by remember { mutableStateOf("") }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isCreateAccountButtonEnabled = true,
                    isEmailValid = false,
                    emailError = emailError
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnEmailChange -> {
                        emailError = "email is not valid"
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.EMAIL_TEST_TAG).performTextInput("J")
        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).performClick()
        composeTestRule.onNodeWithText("email is not valid").assertExists()
    }


    @Test
    fun whenPhoneIsNotValid_ThenPhoneErrorShows(){
        composeTestRule.setContent {
            var phoneError by remember { mutableStateOf("") }
            SignUpContent(
                modifier = Modifier,
                state = SignUpContract.SignupState(
                    isCreateAccountButtonEnabled = true,
                    isPhoneNumberValid = false,
                    phoneError = phoneError
                )
            ) { intent ->
                when(intent){
                    is SignUpContract.SignUpIntent.OnPhoneNumberChange -> {
                        phoneError = "phone is not valid"
                    }

                    else -> {}
                }
            }
        }

        composeTestRule.onNodeWithTag(SignUpTestTag.PHONE_TEST_TAG).performTextInput("010")
        composeTestRule.onNodeWithTag(SignUpTestTag.CONFIRM_BUTTON_TEST_TAG).performClick()
        composeTestRule.onNodeWithText("phone is not valid").assertExists()
    }

}