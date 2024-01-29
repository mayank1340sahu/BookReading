package com.example.bookreading.repository

import com.example.bookreading.network.BookApi
import retrofit2.http.Query

class RepositoryRemake(private val bookApi: BookApi) {
    suspend fun getAllBooks(query: String) = bookApi.getAllBooks(query)

    suspend fun getBookInfo(query: String) = bookApi.bookInfo(query)
}