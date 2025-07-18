package com.example.task_prp.ui.screen.loginscreen

import com.example.task_prp.data.remote.Country
import com.example.task_prp.ui.screen.base.UiEffect
import com.example.task_prp.ui.screen.base.UiIntent
import com.example.task_prp.ui.screen.base.UiState

class LoginContract {


    sealed class LoginIntent:UiIntent {
        data class OnDefaultCountryChange(val countryId: Int?):LoginIntent()
        data object OnShowPasswordClick:LoginIntent()
        data class OnPhoneNumberChange(val newPhoneNumber:String):LoginIntent()
        data class OnPasswordChange(val newPassword: String):LoginIntent()
        data class OnCreateAccountClick(val countryId: Int):LoginIntent()
        data object OnLoginClick:LoginIntent()
        data class OnCountryPickerClick(val countryId: Int):LoginIntent()
    }

    data class LoginState(
        val phoneNumber:String = "",
        val countryId:Int = 0,
        val country: Country? = null,
        val password:String = "",
        val passwordError:String = "",
        val phoneError:String = "",
        val isPhoneValid:Boolean? = null,
        val isPasswordValid:Boolean? = null,
        val isPhoneEmpty:Boolean = true,
        val isPasswordEmpty:Boolean = true,
        val isPasswordHidden:Boolean = true,
        val isProcessButtonEnabled: Boolean = false
    ):UiState

    sealed class LoginEffect:UiEffect{
        data class CreateAccount(val countryId: Int):LoginEffect()
        data object Login:LoginEffect()
        data class CountryPickerClick(val countryId: Int):LoginEffect()
    }


}