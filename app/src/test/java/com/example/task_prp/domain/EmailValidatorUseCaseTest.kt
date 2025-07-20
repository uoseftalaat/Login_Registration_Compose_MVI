package com.example.task_prp.domain

import com.example.task_prp.domain.businessusecase.validator.EmailValidatorUseCase
import com.example.task_prp.domain.businessusecase.validator.UseCaseResult
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmailValidatorUseCaseTest {

    private lateinit var emailValidatorUseCase: EmailValidatorUseCase

    @BeforeEach
    fun setup(){
        emailValidatorUseCase = EmailValidatorUseCase()
    }

    @Test
    fun `when email entered without com then returns false`(){
        val email = "uosef@gmail"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                false,
                "Please Enter a Valid Email Format"
            )
        )
    }

    @Test
    fun `when email entered without gmail then returns false`(){
        val email = "uosef.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                false,
                "Please Enter a Valid Email Format"
            )
        )
    }

    @Test
    fun `when email entered without gmail com then returns false`(){
        val email = "uoseftalaat"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                false,
                "Please Enter a Valid Email Format"
            )
        )
    }

    @Test
    fun `when email entered in the right format gmail com then returns true`(){
        val email = "uosef@gmail.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                true,
                ""
            )
        )
    }

    @Test
    fun `when email entered in the right format hotmail com returns true`(){
        val email = "uosef@hotmail.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                true,
                ""
            )
        )
    }

    @Test
    fun `when custom email from custom domain then returns true`(){
        val email = "uosef@speakwithme.com"

        val result = emailValidatorUseCase(email)

        assertThat(result).isEqualTo(
            UseCaseResult(
                true,
                ""
            )
        )
    }


}