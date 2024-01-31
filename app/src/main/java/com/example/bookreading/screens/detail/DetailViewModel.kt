package com.example.bookreading.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreading.apiData.Item
import com.example.bookreading.repository.BookRepository2
import com.example.bookreading.repository.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repo : BookRepository2) : ViewModel(){
    suspend fun bookInfo(bookId : String): Resources<Item> {
        return repo.getBookInfo(bookId)
    }
}