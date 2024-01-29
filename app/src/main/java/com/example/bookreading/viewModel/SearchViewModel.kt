package com.example.bookreading.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreading.repository.BookRepository2
import com.example.bookreading.repository.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo : BookRepository2): ViewModel() {
    var list : List<com.example.bookreading.apiData.Item> by mutableStateOf(listOf())

    init {
        loadBook()
    }

    private fun loadBook() {
        searchBook("Android")
    }

    fun searchBook(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                when(val response = repo.getAllBooks(query)){
                    is Resources.Success ->{
                        list = response.data!!
                    }
                    is Resources.Error ->{
                        Log.d("ViewModel", "searchBook: ${response.message}")
                    }
                    else ->{

                    }
                }
            }
            catch (e:Exception){
                Log.d("ViewModel", "searchBook: $e")
            }

        }
    }
}