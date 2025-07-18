package com.example.task_prp.domain.repository

import com.example.task_prp.data.remote.datasource.CountryRemoteDataSource
import com.example.task_prp.domain.model.Country

class CountryRepositoryImpl(
    private val remoteDataSource: CountryRemoteDataSource
): CountryRepository {

    override suspend fun getAllCountries(): List<Country> {
        return remoteDataSource.getCountries().map { countryDto ->
            countryDto.let {
                Country(
                    it.countryCode,
                    it.dialCode,
                    it.countryName,
                    it.icon
                )
            }
        }
    }

    override suspend fun getCountryById(id: String): Country {
        return remoteDataSource.getCountryById(id).let {
            Country(
                it.countryCode,
                it.dialCode,
                it.countryName,
                it.icon
            )
        }
    }
}