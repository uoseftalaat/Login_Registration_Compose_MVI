package com.example.task_prp.domain.businessusecase

class EmailValidatorUseCase {

    operator fun invoke(email:String): UseCaseResult {
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return if(EMAIL_REGEX.matches(email)) UseCaseResult(
            true,
            ""
        )
        else UseCaseResult(
            false,
            "Please Enter a Valid Email Format"
        )
    }
}