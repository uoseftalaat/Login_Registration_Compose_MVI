package com.example.task_prp.domain.businessusecase.countryusecase

import com.example.task_prp.domain.model.Country
import com.example.task_prp.domain.repository.CountryRepository

class GetAllCountriesUseCase(
    private val repository: CountryRepository
) {

    suspend operator fun invoke():List<Country> = repository.getAllCountries()
}