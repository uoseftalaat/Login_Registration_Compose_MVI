package com.example.task_prp.domain.repository

import com.example.task_prp.domain.model.Country


interface CountryRepository {

    suspend fun getAllCountries():List<Country>
    suspend fun getCountryByCountryCode(countryCode:String): Country
}