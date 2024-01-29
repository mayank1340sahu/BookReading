package com.example.bookreading.repository

import android.util.Log
import com.example.bookreading.apiData.Item
import com.example.bookreading.network.BookApi
import javax.inject.Inject

class BookRepository2 @Inject constructor(private val bookApi: BookApi) {

    suspend fun getAllBooks(query : String) : Resources<List<Item>>{
        return try {
            Resources.Loading(data = true)
            val itemList = bookApi.getAllBooks(query).items
            if(itemList.isNotEmpty())Resources.Loading(false)
            Resources.Success(data = itemList)
        }
        catch (e:Exception){
            Log.d("Repository", "getAllBooks: $e")
            Resources.Error(message = e.message.toString())
        }
    }

suspend fun getBookInfo(query: String) : Resources<Item> {
     return try {
        Resources.Loading(data = true)
        val itemList = bookApi.bookInfo(query)
         if(itemList.toString().isNotEmpty())Resources.Loading(false)
         Resources.Success(data = itemList)
    }
    catch (e:Exception){
        Resources.Error(message = e.message.toString())
    }
}}