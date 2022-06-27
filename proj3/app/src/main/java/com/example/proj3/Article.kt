package com.example.proj3

import java.io.Serializable

data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val annotation: String,
    val text: String,
    val header: String
) :Serializable
