package com.example.bookreading.network

import com.example.bookreading.apiData.Item
import com.example.bookreading.apiData.books
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String) : books

    @GET("volumes/{bookId}")
    suspend fun bookInfo(@Path("bookId") bookId : String) : Item
}