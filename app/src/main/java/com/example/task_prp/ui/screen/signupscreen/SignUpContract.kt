package com.example.task_prp.ui.screen.signupscreen

import com.example.task_prp.data.remote.Country
import com.example.task_prp.ui.screen.base.UiEffect
import com.example.task_prp.ui.screen.base.UiIntent
import com.example.task_prp.ui.screen.base.UiState

class SignUpContract{

    sealed class SignUpIntent:UiIntent{
        data class OnNameChange(val newName:String):SignUpIntent()
        data class OnSurNameChange(val newSurName:String):SignUpIntent()
        data class OnEmailChange(val newEmail:String):SignUpIntent()
        data class OnFlagChange(val countryId: Int?):SignUpIntent()
        data class OnPhoneNumberChange(val newPhoneNumber:String):SignUpIntent()
        data class OnCountryPickClick(val countryId: Int):SignUpIntent()
        data class OnBackButtonClick(val countryId: Int):SignUpIntent()
        data object OnConfirmClick:SignUpIntent()
    }

    data class SignupState(
        val name:String = "",
        val surName:String = "",
        val email:String = "",
        val phoneNumber:String = "",
        val country: Country? = null,
        val countryId:Int? = null,
        val isNameValid:Boolean? = null,
        val isSurNameValid:Boolean? = null,
        val isEmailValid:Boolean? = null,
        val isPhoneNumberValid:Boolean? = null,
        val isNameEmpty:Boolean = true,
        val isSurNameEmpty:Boolean = true,
        val isEmailEmpty:Boolean = true,
        val isPhoneNumberEmpty:Boolean = true,
        val isCreateAccountButtonEnabled:Boolean = false,
        val nameError:String = "",
        val surNameError:String = "",
        val emailError:String = "",
        val phoneError:String = ""
    ):UiState

    sealed class SignUpEffect:UiEffect{
        data class OnCreateAccountClick(val countryId:Int):SignUpEffect()
        data class OnBackButtonClick(val countryId: Int):SignUpEffect()
        data class OnCountryPickClick(val countryId: Int):SignUpEffect()
    }
}