package com.example.task_prp.ui.screen.loginscreen

import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.connectivity.ConnectivityObserver
import com.example.task_prp.ui.screen.base.UiEffect
import com.example.task_prp.ui.screen.base.UiIntent
import com.example.task_prp.ui.screen.base.UiState

class LoginContract {


    sealed class LoginIntent:UiIntent {
        data class OnDefaultCountryChange(val countryCode: String?):LoginIntent()
        data object OnShowPasswordClick:LoginIntent()
        data class OnPhoneNumberChange(val newPhoneNumber:String):LoginIntent()
        data class OnPasswordChange(val newPassword: String):LoginIntent()
        data class OnCreateAccountClick(val countryCode: String):LoginIntent()
        data object OnLoginClick:LoginIntent()
        data class OnCountryPickerClick(val countryCode: String):LoginIntent()
    }

    data class LoginState(
        val phoneNumber:String = "",
        val countryCode:String? = null,
        val country: Country? = null,
        val password:String = "",
        val passwordError:String = "",
        val phoneError:String = "",
        val isPhoneValid:Boolean? = null,
        val isPasswordValid:Boolean? = null,
        val isPhoneEmpty:Boolean = true,
        val isPasswordEmpty:Boolean = true,
        val isPasswordHidden:Boolean = true,
        val isProcessButtonEnabled:Boolean = false
    ):UiState

    sealed class LoginEffect:UiEffect{
        data class CreateAccount(val countryCode: String):LoginEffect()
        data object Login:LoginEffect()
        data class CountryPickerClick(val countryCode: String):LoginEffect()
    }


}