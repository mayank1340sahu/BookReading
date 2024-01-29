package com.example.bookreading.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreading.apiData.Item
import com.example.bookreading.repository.BookRepository
import com.example.bookreading.repository.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repo : BookRepository):ViewModel() {
    val listOfBooks : MutableState<DataOrException<List<Item>, Boolean, Exception>>
            = mutableStateOf(DataOrException(null,true,java.lang.Exception("")))
    init {
        Log.d("viewModelInit", "listofBooks:$listOfBooks ")
        searchBook("one piece")
    }

   fun searchBook(query: String) {
        viewModelScope.launch {
            try{
                listOfBooks.value = repo.getAllBooks(query)
                Log.d("SearchViewModel", "searchBook: ${listOfBooks.value.data.toString()}")
            }
            catch (e:Exception){
                listOfBooks.value.exception = e
            }
        }
    }
}