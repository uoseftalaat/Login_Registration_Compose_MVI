package com.example.task_prp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class PasswordValidatorUseCaseTest {

    private lateinit var passwordValidatorUseCase: PasswordValidatorUseCase

    @Before
    fun setup(){
        passwordValidatorUseCase = PasswordValidatorUseCase()
    }

    @Test
    fun `when enter password lenght less than 6 then returns false`(){
        val password = "Hallo"

        val result = passwordValidatorUseCase(password)

        assertThat(result).isFalse()
    }

    @Test
    fun `when enter password lenght is equal 6 then returns false`(){
        val password = "Hallot"

        val result = passwordValidatorUseCase(password)

        assertThat(result).isTrue()
    }

    @Test
    fun `when enter password lenght is greater than 6 then returns false`(){
        val password = "arsthsf"

        val result = passwordValidatorUseCase(password)

        assertThat(result).isTrue()
    }
}