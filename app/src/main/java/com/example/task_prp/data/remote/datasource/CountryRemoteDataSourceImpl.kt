package com.example.task_prp.data.remote.datasource

import com.apollographql.apollo.ApolloClient
import com.example.task_prp.CountriesQuery
import com.example.task_prp.CountryQuery
import com.example.task_prp.data.remote.dto.CountryDto

class CountryRemoteDataSourceImpl(
    private val apolloClient: ApolloClient
): CountryRemoteDataSource {
    override suspend fun getCountries(): List<CountryDto> {
        val response = apolloClient.query(CountriesQuery()).execute()

        return response.data?.countries?.map { country ->
            country.let {
                CountryDto(
                    it.code,
                    it.phone,
                    it.name,
                    it.emoji
                )
            }
        } ?: emptyList()
    }

    override suspend fun getCountryById(id: String): CountryDto {
        val response = apolloClient.query(CountryQuery(id)).execute()

        return response.data?.country?.let {
            CountryDto(
                it.code,
                it.phone,
                it.name,
                it.emoji
            )
        } ?: CountryDto()
    }
}