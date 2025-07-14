package com.example.task_prp.domain

class PasswordValidatorUseCase {
    operator fun invoke(password:String):Boolean{
        return password.length >= 6
    }
}