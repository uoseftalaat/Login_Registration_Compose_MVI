package com.example.task_prp.data.remote.datasource

import com.example.task_prp.data.remote.dto.CountryDto


interface CountryRemoteDataSource {

    suspend fun getCountries():List<CountryDto>

    suspend fun getCountryById(id:String): CountryDto
}