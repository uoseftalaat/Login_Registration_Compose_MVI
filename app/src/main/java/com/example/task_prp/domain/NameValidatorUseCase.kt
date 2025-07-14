package com.example.task_prp.domain

class NameValidatorUseCase {
    operator fun invoke(name:String):Boolean{
        return name.length >= 2
    }
}