package com.example.task_prp.di

import com.example.task_prp.data.remote.datasource.CountryRemoteDataSource
import com.example.task_prp.data.remote.datasource.CountryRemoteDataSourceImpl
import com.example.task_prp.data.remote.repository.CountryRepositoryImpl
import com.example.task_prp.domain.businessusecase.countryusecase.GetAllCountriesUseCase
import com.example.task_prp.domain.businessusecase.countryusecase.GetCountryByCountryCodeUseCase
import com.example.task_prp.domain.repository.CountryRepository
import org.koin.dsl.module

val domainModule = module {

    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    single<CountryRemoteDataSource> {
        CountryRemoteDataSourceImpl(get())
    }

    factory<GetCountryByCountryCodeUseCase> { GetCountryByCountryCodeUseCase(get()) }

    factory<GetAllCountriesUseCase> { GetAllCountriesUseCase(get()) }
}