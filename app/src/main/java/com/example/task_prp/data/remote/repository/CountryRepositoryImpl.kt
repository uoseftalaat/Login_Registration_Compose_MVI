package com.example.task_prp.data.remote.repository

import com.example.task_prp.data.remote.datasource.CountryRemoteDataSource
import com.example.task_prp.data.remote.mapper.toDomain
import com.example.task_prp.domain.model.Country
import com.example.task_prp.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val remoteDataSource: CountryRemoteDataSource
): CountryRepository {

    override suspend fun getAllCountries(): List<Country> {
        return remoteDataSource.getCountries().map {it.toDomain()}
    }

    override suspend fun getCountryById(id: String): Country {
        return remoteDataSource.getCountryById(id).toDomain()
    }
}