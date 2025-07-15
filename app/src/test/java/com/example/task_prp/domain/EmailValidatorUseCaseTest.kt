package com.example.task_prp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class EmailValidatorUseCaseTest {

    private lateinit var emailValidatorUseCase:EmailValidatorUseCase

    @Before
    fun setup(){
        emailValidatorUseCase = EmailValidatorUseCase()
    }

    @Test
    fun `when email entered without com then returns false`(){
        val email = "uosef@gmail"

        val result = emailValidatorUseCase(email)

        assertThat(result).isFalse()
    }

    @Test
    fun `when email entered without gmail then returns false`(){
        val email = "uosef.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isFalse()
    }

    @Test
    fun `when email entered without gmail com then returns false`(){
        val email = "uoseftalaat"

        val result = emailValidatorUseCase(email)

        assertThat(result).isFalse()
    }

    @Test
    fun `when email entered in the right format gmail com then returns true`(){
        val email = "uosef@gmail.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isTrue()
    }

    @Test
    fun `when email entered in the right format hotmail com returns true`(){
        val email = "uosef@hotmail.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isTrue()
    }

    @Test
    fun `when custom email from custom domain then returns true`(){
        val email = "uosef@speakwithme.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isTrue()
    }


}