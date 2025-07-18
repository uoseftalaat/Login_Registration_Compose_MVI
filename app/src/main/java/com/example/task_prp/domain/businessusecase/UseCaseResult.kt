package com.example.task_prp.domain.businessusecase

data class UseCaseResult(
    var isEntryValid:Boolean,
    var errorMessage:String = ""
)