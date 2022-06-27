package com.example.extendedshoppinglist

import java.io.Serializable
import  com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Shopping(
    val name : String,
    val quantity: String
) : Serializable
