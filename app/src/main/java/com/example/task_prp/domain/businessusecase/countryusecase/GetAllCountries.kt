package com.example.task_prp.domain.businessusecase.countryusecase

import com.example.task_prp.domain.model.Country

interface GetAllCountries {

    suspend operator fun invoke():List<Country>
}