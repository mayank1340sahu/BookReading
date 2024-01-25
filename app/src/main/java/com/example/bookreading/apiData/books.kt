package com.example.bookreading.apiData

data class books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)