package com.example.task_prp.domain

import com.google.common.truth.Truth.assertThat
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.junit.Before
import org.junit.Test

class PhoneNumberValidatorUseCaseTest {

    private lateinit var phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase

    @Before
    fun setup(){
        phoneNumberValidatorUseCase = PhoneNumberValidatorUseCase(phoneNumberUtil = PhoneNumberUtil.getInstance())
    }

    @Test
    fun `when user enter phone number correctly then return true`(){
        val phoneNumber = "01034578323"
        val countryCode = "20"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            true,
            ""
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when user enter phone number in a wrong form less than the lenght required then return false`(){
        val phoneNumber = "0104354738"
        val countryCode = "20"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            false,
            "Phone number is not in a valid format"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when user enter phone number in a wrong form more than the lenght required then return false`(){
        val phoneNumber = "010435473879"
        val countryCode = "20"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            false,
            "Phone number is not in a valid format"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when user enter letter in the phone number then return false`(){
        val phoneNumber = "arstn"
        val countryCode = "20"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            false,
            "Please enter numbers only"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when user enter letter and numbers in the phone number then return false`(){
        val phoneNumber = "010uaer"
        val countryCode = "20"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            false,
            "Phone number is not in a valid format"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `when user enter phone format for different country code then return false`(){
        val phoneNumber = "01063935766"
        val countryCode = "1"

        val result = phoneNumberValidatorUseCase(phoneNumber,countryCode)

        val expected = PhoneNumberValidationResults(
            false,
            "Phone number is not in a valid format"
        )
        assertThat(result).isEqualTo(expected)
    }
}