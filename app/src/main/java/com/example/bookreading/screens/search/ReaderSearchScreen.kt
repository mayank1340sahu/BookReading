package com.example.bookreading.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.bookreading.data.MBook
import com.example.bookreading.screens.home.ReaderTopBar
import com.example.bookreading.screens.login.LoginViewModel
import com.example.bookreading.screens.login.widgt.InputField
import com.example.bookreading.viewModel.BookViewModel
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderSearchScreen(
    navController: NavHostController = NavHostController((LocalContext.current)),
    viewModel: LoginViewModel = hiltViewModel(),
    bookViewModel: BookViewModel = hiltViewModel(),
) {
    val onePiece = "https://www.bing.com/th?id=OIP.jMfANDS0wBX5OguqpK7MrAHaKR&w=150&h=208&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2"
    val naruto = "https://th.bing.com/th/id/OIP.EjIl-g-wSybkVtNApisWMwHaLH?rs=1&pid=ImgDetMain"
    val berserk = "https://th.bing.com/th/id/OIP.T-OD2pGl9-W6uLnY0D_jKQHaKe?rs=1&pid=ImgDetMain"
    val  vinland = "https://th.bing.com/th/id/OIP.oot8TxRDQtGdbkNHi3E-pwHaKb?rs=1&pid=ImgDetMain"
    val haikyuu = "https://th.bing.com/th/id/OIP.uTpLPml-BwKfT6EV1RtZsQHaK9?rs=1&pid=ImgDetMain"
    val deathNote = "https://th.bing.com/th/id/OIP.ugzcbjSxJc5XeUUDXYkH1wHaK5?rs=1&pid=ImgDetMain"
    val aot = "https://th.bing.com/th/id/OIP.TfR7XD-Y6UZDt-uLfGzu0AHaKe?rs=1&pid=ImgDetMain"
   val bookList = listOf(
        MBook("234","One piece", author = "ichiro oda",
            notes = "i am going to be king of the pirates",onePiece),
        MBook("234","Naruto", author = "Kisimoto",
            notes = "dattebayo",naruto),
        MBook("234","Berserk", author = "Kentaro Miura",
            notes = "i want to be happy",berserk),
        MBook("234","Vinland saga", author = "Makoto Yukimura",
            notes = "i have no enemies",vinland),
        MBook("234","Haikyuu!", author = "Haruichi Furudate",
            notes = "hunger is necessary for growth",haikyuu),
        MBook("234","Death Note", author = "Tsugumi Ohba",
            notes = "bokuva kira da",deathNote),
        MBook("234","Attack on titan", author = "Hajime Isayama",
            notes = "tatakaye",aot),
    )
    Scaffold (
        topBar = {
       ReaderTopBar(viewModel,navController,
                    title = "Search Books"
       ) {
           IconButton(onClick = { navController.popBackStack() }) {
               Icon(imageVector = Icons.Default.ArrowBack,
                   contentDescription = "back")
           }
       }
        },
    ){
        Column{
            SearchInput(
                modifier = Modifier.heightIn(50.dp),
                bookViewModel,
                paddingValues = it) {
                bookViewModel.searchBook(it)
            }
            Spacer(modifier = Modifier.height(3.dp))
            BookList(bookList)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel,
    paddingValues: PaddingValues,
    onSearch: (String) -> Unit
) {
    val value = rememberSaveable{
        mutableStateOf("")
    }
    val valid = remember(value.value) {
       value.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    InputField(valueState = value, labelId ="Books" , enable = true,
        onAction = KeyboardActions{
            Log.d("onAction", "SearchInput: action")
            if (!valid) return@KeyboardActions
            onSearch(value.value.trim())
            Log.d("onSearch", "SearchInput: ${value.value}")
            value.value = ""
            keyboardController?.hide()
        }, modifier = modifier.padding(paddingValues)
    ) {
        
    }
}

@Preview
@Composable
fun BookRow(mBook: MBook =MBook("234","One piece", author = "ichiro oda",
    notes = "i am going to be king of the pirates",
    "https://www.bing.com/th?id=OIP.jMfANDS0wBX5OguqpK7MrAHaKR&w=150&h=208&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2")) {
   Card(shape = RectangleShape,
       elevation = CardDefaults.elevatedCardElevation(5.dp),
       colors = CardDefaults.cardColors(containerColor = Color.White,
           contentColor = Color.Black),
       modifier = Modifier
           .fillMaxWidth()
           .padding(5.dp)
           .heightIn(100.dp)) {
       Row(Modifier.fillMaxWidth()) {
           Log.d("SearchScreen", "BookRow: ${mBook.imageUrl}")
           Image(painter = rememberImagePainter(data = mBook.imageUrl),
               contentDescription = "book", modifier = Modifier
                   .width(70.dp)
                   .height(100.dp)
                   .padding(2.dp))
           Column {
               Text(text = mBook.title.toString(),
                   fontSize = 20.sp)
               Text(text = mBook.author.toString(),
                   fontStyle = FontStyle.Italic,
                   fontSize = 16.sp)
               Text(text = "Date: 23/01/2024",
                   fontStyle = FontStyle.Italic,
                   fontSize = 16.sp)
               Text(text = "[Manga]",
                   fontStyle = FontStyle.Italic,
                   fontSize = 16.sp)
           }
       }
   }
}

@Composable
fun BookList( bookList: List<MBook>) {
    LazyColumn(modifier = Modifier.padding(6.dp),content = {
        items(bookList) {
            BookRow(it)
        }
    })
}
