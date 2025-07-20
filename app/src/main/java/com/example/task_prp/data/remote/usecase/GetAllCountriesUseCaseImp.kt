package com.example.task_prp.data.remote.usecase

import com.example.task_prp.domain.businessusecase.countryusecase.GetAllCountries
import com.example.task_prp.domain.model.Country
import com.example.task_prp.domain.repository.CountryRepository

class GetAllCountriesUseCaseImp(
    private val repo:CountryRepository
):GetAllCountries {
    override suspend fun invoke(): List<Country> = repo.getAllCountries()
}