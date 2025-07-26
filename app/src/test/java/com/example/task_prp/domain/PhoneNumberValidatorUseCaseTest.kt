package com.example.task_prp.domain

import com.example.task_prp.domain.businessusecase.validator.PhoneNumberValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.UseCaseResult
import com.google.common.truth.Truth.assertThat
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class PhoneNumberValidatorUseCaseTest1{

    private lateinit var phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase

    @Mock
    private lateinit var phoneNumberUtil: PhoneNumberUtil

    @BeforeEach
    fun setup(){
        phoneNumberValidatorUseCase = PhoneNumberValidatorUseCase(phoneNumberUtil = phoneNumberUtil)
    }


    @Test
    fun `when user enter phone number correctly then return true`(){
        val phoneNumber = "01034578323"
        val countryCode = "EG"

        val parsedNumber: Phonenumber.PhoneNumber = Phonenumber.PhoneNumber()

        Mockito.`when`(phoneNumberUtil.parse(phoneNumber,countryCode)).then {
            Phonenumber.PhoneNumber()
        }

        Mockito.`when`(phoneNumberUtil.isValidNumberForRegion(parsedNumber, countryCode)).then {
            true
        }

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = UseCaseResult(
            true,
            ""
        )
        assertThat(result).isEqualTo(expected)
        verify(phoneNumberUtil).isValidNumberForRegion(parsedNumber,countryCode)
    }
}