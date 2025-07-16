package com.example.task_prp.domain

data class UseCaseResult(
    var isEntryValid:Boolean,
    var errorMessage:String = ""
)