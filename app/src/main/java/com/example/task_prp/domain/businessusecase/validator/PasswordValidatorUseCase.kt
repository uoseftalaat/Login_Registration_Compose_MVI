package com.example.task_prp.domain.businessusecase.validator

class PasswordValidatorUseCase {
    operator fun invoke(password:String): UseCaseResult {

        return if(password.length >= 6) UseCaseResult(
            true,
            ""
        )
        else UseCaseResult(
            false,
            "The minimum lenght for password is 6 characters"
        )
    }
}