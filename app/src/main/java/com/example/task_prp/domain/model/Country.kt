package com.example.task_prp.domain.model

data class Country(
    val countryCode:String = "",
    val dialCode:String = "",
    val countryName:String = "",
    val icon: String = "",
    var isPicked:Boolean = false
)