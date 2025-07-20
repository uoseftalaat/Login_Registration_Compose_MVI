package com.example.task_prp.domain.businessusecase.countryusecase

import com.example.task_prp.domain.model.Country

interface GetCountryByCountryCode {

    suspend operator fun invoke(countryCode:String):Country
}