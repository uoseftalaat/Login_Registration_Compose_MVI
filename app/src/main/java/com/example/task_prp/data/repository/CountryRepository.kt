package com.example.task_prp.data.repository

import com.example.task_prp.data.Country

interface CountryRepository {

    suspend fun getAllCountries():List<Country>

    suspend fun getCountryById(id:Int): Country
}