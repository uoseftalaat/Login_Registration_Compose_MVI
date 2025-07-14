package com.example.task_prp.di

import androidx.lifecycle.SavedStateHandle
import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.data.repository.CountryRepositoryImpl
import com.example.task_prp.domain.EmailValidatorUseCase
import com.example.task_prp.domain.NameValidatorUseCase
import com.example.task_prp.domain.PasswordValidatorUseCase
import com.example.task_prp.domain.PhoneNumberValidationResults
import com.example.task_prp.domain.PhoneNumberValidatorUseCase
import com.example.task_prp.ui.screen.countrypickerScreen.CountryPickerViewModel
import com.example.task_prp.ui.screen.loginscreen.LoginViewModel
import com.example.task_prp.ui.screen.signupscreen.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<CountryRepository> {
        CountryRepositoryImpl()
    }

    single { EmailValidatorUseCase() }

    single { NameValidatorUseCase() }

    single { PasswordValidatorUseCase() }

    single { PhoneNumberValidatorUseCase() }


    viewModel {
        LoginViewModel(get(),get(),get(),get())
    }


    viewModel {
        SignUpViewModel(get(),get(),get(),get())
    }


    viewModel {
        CountryPickerViewModel(get(), get())
    }

}