package com.example.task_prp.domain.businessusecase.countryusecase

import com.example.task_prp.domain.model.Country
import com.example.task_prp.domain.repository.CountryRepository

class GetCountryByCountryCodeUseCase(
    private val repository:CountryRepository
) {

     suspend operator fun invoke(countryCode:String):Country{
         return repository.getCountryByCountryCode(countryCode)
     }
}