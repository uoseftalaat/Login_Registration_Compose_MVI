package com.example.task_prp.domain.businessusecase.validator

class NameValidatorUseCase {
    operator fun invoke(name:String): UseCaseResult {
        return if(name.length >= 2) UseCaseResult(
            true,
            ""
        )
        else UseCaseResult(
            false,
            "Name Must have at least 2 letters"
        )
    }
}