package com.example.task_prp.di

import androidx.lifecycle.SavedStateHandle
import com.example.task_prp.data.repository.CountryRepository
import com.example.task_prp.data.repository.CountryRepositoryImpl
import com.example.task_prp.ui.screen.countrypickerScreen.CountryPickerViewModel
import com.example.task_prp.ui.screen.loginscreen.LoginViewModel
import com.example.task_prp.ui.screen.signupscreen.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = listOf(
    authModule
)