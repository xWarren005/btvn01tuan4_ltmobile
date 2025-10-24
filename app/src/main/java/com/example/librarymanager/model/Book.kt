package com.example.librarymanager.model

data class Book(
    val id: Int,
    val title: String,
    var isBorrowed: Boolean = false
)
