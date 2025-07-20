package com.example.task_prp.data.remote.usecase

import com.example.task_prp.domain.businessusecase.countryusecase.GetCountryByCountryCode
import com.example.task_prp.domain.repository.CountryRepository

class GetCountryByCountryCodeImp(
    private val repo:CountryRepository
): GetCountryByCountryCode {
    override suspend fun invoke(countryCode: String) = repo.getCountryByCountryCode(countryCode)
}