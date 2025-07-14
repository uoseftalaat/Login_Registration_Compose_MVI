package com.example.task_prp.data

data class Country(
    val id:Int = 0,
    val icon: Int = 0,
    val countryCode:String = "",
    val countryName:String = "",
    var isPicked:Boolean = false
)
