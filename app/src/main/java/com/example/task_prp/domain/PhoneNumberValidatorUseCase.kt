package com.example.task_prp.domain

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

class PhoneNumberValidatorUseCase(
    private val phoneNumberUtil: PhoneNumberUtil
){
    operator fun invoke(phoneNumber:String,countryCode:String):PhoneNumberValidationResults{

        try {
            val regionCode = phoneNumberUtil.getRegionCodeForCountryCode(
                countryCode.toInt()
            )
            val parsedNumber: Phonenumber.PhoneNumber = phoneNumberUtil.parse(phoneNumber, regionCode)

            val result = phoneNumberUtil.isValidNumberForRegion(parsedNumber, regionCode)

            return if(result)
                PhoneNumberValidationResults(true)
                else
                    PhoneNumberValidationResults(
                false,
                    "Phone number is not in a valid format"
                )

        } catch (e: NumberParseException) {
            return PhoneNumberValidationResults(
                false,
                "Please enter numbers only"
            )
        }
    }
}

data class PhoneNumberValidationResults(
    var isPhoneValid:Boolean,
    var errorMessage:String = ""
)