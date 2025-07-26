package com.example.task_prp.domain.businessusecase.validator

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

class PhoneNumberValidatorUseCase(
    private val phoneNumberUtil: PhoneNumberUtil
){
    operator fun invoke(phoneNumber:String,countryCode:String): UseCaseResult {

        try {
            val parsedNumber: Phonenumber.PhoneNumber = phoneNumberUtil.parse(phoneNumber, countryCode)

            val result = phoneNumberUtil.isValidNumberForRegion(parsedNumber, countryCode)

            return if(result)
                UseCaseResult(true)
                else
                    UseCaseResult(
                false,
                    "Phone number is not in a valid format"
                )

        } catch (e: NumberParseException) {
            return UseCaseResult(
                false,
                "Please enter numbers only"
            )
        }
    }
}