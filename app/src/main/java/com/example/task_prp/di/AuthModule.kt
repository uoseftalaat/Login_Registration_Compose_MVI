package com.example.task_prp.di

import com.apollographql.apollo.ApolloClient
import com.example.task_prp.data.remote.datasource.CountryRemoteDataSource
import com.example.task_prp.data.remote.datasource.CountryRemoteDataSourceImpl
import com.example.task_prp.domain.repository.CountryRepository
import com.example.task_prp.data.remote.repository.CountryRepositoryImpl
import com.example.task_prp.domain.businessusecase.validator.EmailValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.NameValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.PasswordValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.PhoneNumberValidatorUseCase
import com.example.task_prp.ui.connectivity.ConnectionObserverImpl
import com.example.task_prp.ui.connectivity.ConnectivityObserver
import com.example.task_prp.ui.screen.countrypickerScreen.CountryPickerViewModel
import com.example.task_prp.ui.screen.loginscreen.LoginViewModel
import com.example.task_prp.ui.screen.signupscreen.SignUpViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {

    single {
        ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    single<CountryRemoteDataSource> {
        CountryRemoteDataSourceImpl(get())
    }

    single { EmailValidatorUseCase() }

    single<ConnectivityObserver>{
        ConnectionObserverImpl(get())
    }

    single { NameValidatorUseCase() }

    single { PasswordValidatorUseCase() }

    single { PhoneNumberUtil.getInstance() }

    single { PhoneNumberValidatorUseCase(get()) }


    viewModelOf(::LoginViewModel)


    viewModelOf(::SignUpViewModel)


    viewModelOf(::CountryPickerViewModel)

}