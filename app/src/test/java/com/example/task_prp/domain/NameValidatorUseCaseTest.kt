package com.example.task_prp.domain

import org.junit.Test
import com.google.common.truth.Truth.*
import org.junit.Before

class NameValidatorUseCaseTest {


    private lateinit var nameValidatorUseCase:NameValidatorUseCase

    @Before
    fun before(){
        nameValidatorUseCase = NameValidatorUseCase()
    }

    @Test
    fun `when enter name lenght less than 2 letters then return false`(){
        val name = "j"

        val result = nameValidatorUseCase(name)

        assertThat(result).isFalse()
    }

    @Test
    fun `when enter name lenght equals 2 letters then return false`(){
        val name = "Jo"

        val result = nameValidatorUseCase(name)

        assertThat(result).isTrue()
    }

    @Test
    fun `when enter name lenght greater than 2 letters then return false`(){
        val name = "Joshua"

        val result = nameValidatorUseCase(name)

        assertThat(result).isTrue()
    }
}