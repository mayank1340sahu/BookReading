package com.example.bookreading.screens.detail

import android.text.Html
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookreading.apiData.Item
import com.example.bookreading.repository.Resources
import com.example.bookreading.screens.home.ReaderTopBar
import com.example.bookreading.screens.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetails(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    bookId: String,
) {
    val bookInfo = produceState<Resources<Item>>(initialValue = Resources.Loading()){
        value = detailViewModel.bookInfo(bookId)
    }.value
    Scaffold(topBar = { ReaderTopBar(viewModel = LoginViewModel(),
        navController = navController,
        title ="Detail Screen") {

    }}) {
        val scrollState = rememberScrollState()
            Column(
                Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally) {

                if (bookInfo.data != null) {
                   val description = Html.fromHtml(bookInfo.data.volumeInfo.description,
                       Html.FROM_HTML_MODE_LEGACY).toString()
                    Text(text = "Book Description", fontSize = 20.sp, textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth())
                    Text(text = description, modifier = Modifier.padding(horizontal = 7.dp), fontSize = 14.sp) }
                else{
                    LinearProgressIndicator()
               }
            }

    }
}
