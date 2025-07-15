package com.example.task_prp.domain

class EmailValidatorUseCase {

    operator fun invoke(email:String):Boolean{
        val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return EMAIL_REGEX.matches(email)
    }
}