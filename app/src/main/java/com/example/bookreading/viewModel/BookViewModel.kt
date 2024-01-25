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
class BookViewModel @Inject constructor(val repo : BookRepository):ViewModel() {
private val listOfBooks :MutableState<DataOrException<List<Item>,Boolean,Exception>>
    = mutableStateOf(DataOrException(null,true,java.lang.Exception("")))
     init {
         searchBook("one piece")
     }

 fun searchBook(query: String) {
        viewModelScope.launch {
            try{
                listOfBooks.value.loading = true
                listOfBooks.value.data = repo.getAllBooks(query).data
                Log.d("SearchViewModel", "searchBook: ${listOfBooks.value.data.toString()}")
                if (listOfBooks.value.data!!.toString().isNotEmpty())

                    listOfBooks.value.loading = false
            }
            catch (e:Exception){
                listOfBooks.value.exception = e
            }
        }
    }
}