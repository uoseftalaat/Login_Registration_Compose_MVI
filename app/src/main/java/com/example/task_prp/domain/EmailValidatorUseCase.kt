package com.example.task_prp.domain

class EmailValidatorUseCase {

    operator fun invoke(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}