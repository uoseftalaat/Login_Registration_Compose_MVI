package com.example.task_prp.domain

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NameValidatorUseCaseTest {


    private lateinit var nameValidatorUseCase:NameValidatorUseCase

    @BeforeEach
    fun before(){
        nameValidatorUseCase = NameValidatorUseCase()
    }

    @Test
    fun `when enter name lenght less than 2 letters then return false`(){
        val name = "j"

        val result = nameValidatorUseCase(name)

        assertThat(result).isEqualTo(
            UseCaseResult(
                false,
                "Name Must have at least 2 letters"
            )
        )
    }

    @Test
    fun `when enter name lenght equals 2 letters then return true`(){
        val name = "Jo"

        val result = nameValidatorUseCase(name)

        assertThat(result).isEqualTo(
            UseCaseResult(
                true,
                ""
            )
        )
    }

    @Test
    fun `when enter name lenght greater than 2 letters then return false`(){
        val name = "Joshua"

        val result = nameValidatorUseCase(name)

        assertThat(result).isEqualTo(
            UseCaseResult(
                true,
                ""
            )
        )
    }
}