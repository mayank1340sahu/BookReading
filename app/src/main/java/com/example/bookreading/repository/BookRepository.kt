package com.example.bookreading.repository

import android.util.Log
import com.example.bookreading.apiData.Item
import com.example.bookreading.apiData.books
import com.example.bookreading.network.BookApi
import retrofit2.http.Query
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApi: BookApi) {
    private val dataOrException = DataOrException<List<Item>,Boolean,Exception>()
    private val bookdataOrException = DataOrException<Item,Boolean,Exception>()
    suspend fun getAllBooks(query: String):DataOrException<List<Item>,Boolean,Exception> {
        try {
            dataOrException.loading = true
           dataOrException.data = bookApi.getAllBooks(query).items
            if (dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false }
       }
       catch (e:Exception){
           dataOrException.exception = e
       }
        Log.d("Repository", "getAllBooks: $dataOrException")
        return dataOrException
    }
    suspend fun getBookInfo(bookId : String) :DataOrException<Item,Boolean,Exception>{
       try {
           bookdataOrException.loading = true
           bookdataOrException.data = bookApi.bookInfo(bookId)
           if(bookdataOrException.data!!.toString().isNotEmpty())
               bookdataOrException.loading = false
       }catch (e:Exception){
           bookdataOrException.exception = e
       }
        Log.d("Repository", "getBookInfo: $dataOrException")
        return bookdataOrException
    }
}