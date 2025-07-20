package com.example.task_prp.domain.businessusecase.validator

data class UseCaseResult(
    var isEntryValid:Boolean,
    var errorMessage:String = ""
)