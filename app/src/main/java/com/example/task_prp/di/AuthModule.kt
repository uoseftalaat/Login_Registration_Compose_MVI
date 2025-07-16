package com.example.task_prp.di

import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.data.repository.CountryRepositoryImpl
import com.example.task_prp.domain.EmailValidatorUseCase
import com.example.task_prp.domain.NameValidatorUseCase
import com.example.task_prp.domain.PasswordValidatorUseCase
import com.example.task_prp.domain.PhoneNumberValidatorUseCase
import com.example.task_prp.ui.screen.countrypickerScreen.CountryPickerViewModel
import com.example.task_prp.ui.screen.loginscreen.LoginViewModel
import com.example.task_prp.ui.screen.signupscreen.SignUpViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    single<CountryRepository> {
        CountryRepositoryImpl()
    }

    single { EmailValidatorUseCase() }

    single { NameValidatorUseCase() }

    single { PasswordValidatorUseCase() }

    single { PhoneNumberUtil.getInstance() }

    single { PhoneNumberValidatorUseCase(get()) }


    viewModelOf(::LoginViewModel)


    viewModelOf(::SignUpViewModel)


    viewModelOf(::CountryPickerViewModel)

}