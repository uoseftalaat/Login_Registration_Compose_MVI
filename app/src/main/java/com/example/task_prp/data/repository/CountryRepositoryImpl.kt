package com.example.task_prp.data.repository

import com.example.task_prp.R
import com.example.task_prp.data.Country

class CountryRepositoryImpl:CountryRepository {

    private val countries = listOf(
        Country(0, R.drawable.egyptflag,"20","Egypt"),
        Country(1, R.drawable.saudiflag,"966","Saudi Arabia"),
        Country(2, R.drawable.spainflag,"34","Spain"),
        Country(3, R.drawable.canadaflag,"1","Canada"),
        Country(4, R.drawable.franceflag,"20","France"),
        Country(5, R.drawable.usaflag,"1","USA"),
        Country(6, R.drawable.italyflag,"39","Italy"),
    )

    override suspend fun getAllCountries(): List<Country> {
        return countries
    }

    override suspend fun getCountryById(id: Int): Country {
        return countries[id]
    }
}