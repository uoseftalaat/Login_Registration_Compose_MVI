package com.example.task_prp.di

import com.example.task_prp.data.remote.datasource.CountryRemoteDataSource
import com.example.task_prp.data.remote.datasource.CountryRemoteDataSourceImpl
import com.example.task_prp.data.remote.repository.CountryRepositoryImpl
import com.example.task_prp.data.remote.usecase.GetAllCountriesUseCaseImp
import com.example.task_prp.data.remote.usecase.GetCountryByCountryCodeImp
import com.example.task_prp.domain.businessusecase.countryusecase.GetAllCountries
import com.example.task_prp.domain.businessusecase.countryusecase.GetCountryByCountryCode
import com.example.task_prp.domain.repository.CountryRepository
import org.koin.dsl.module

val domainModule = module{
    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    single<CountryRemoteDataSource> {
        CountryRemoteDataSourceImpl(get())
    }

    factory<GetCountryByCountryCode> { GetCountryByCountryCodeImp(get()) }

    factory<GetAllCountries> { GetAllCountriesUseCaseImp(get()) }
}