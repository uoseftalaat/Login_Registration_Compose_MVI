package com.example.task_prp.data.remote.mapper

import com.example.task_prp.data.remote.dto.CountryDto
import com.example.task_prp.domain.model.Country

fun CountryDto.toDomain(): Country {
    return Country(
        countryCode = this.countryCode,
        dialCode = this.dialCode,
        countryName = this.countryName,
        icon = this.icon
    )
}