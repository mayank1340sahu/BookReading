package com.example.bookreading.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.bookreading.R
import com.example.bookreading.data.MBook
import com.example.bookreading.navigation.ReaderScreens
import com.example.bookreading.screens.home.widgt.ListCard
import com.example.bookreading.screens.home.widgt.ReadingList
import com.example.bookreading.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderHomeScreen(navController: NavHostController,
                     viewModel: LoginViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

   Scaffold (topBar = { ReaderTopBar(viewModel,navController,"Book Reading"
   ) {
       Image(
           painter = painterResource(id = R.drawable.book),
           contentDescription = "AppIcon",
           modifier = Modifier.size(30.dp)
       )
   }
   },
       floatingActionButton = {FAB(){
           navController.navigate(ReaderScreens.Search.name)
       } }){
       Column(
           Modifier
               .padding(it)
               .fillMaxSize()
               .verticalScroll(scrollState)) {
    MainContent(viewModel)
            }
       }
   }

@Composable
fun MainContent(viewModel: LoginViewModel) {
    val onePiece = "https://www.bing.com/th?id=OIP.jMfANDS0wBX5OguqpK7MrAHaKR&w=150&h=208&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2"
    val naruto = "https://th.bing.com/th/id/OIP.EjIl-g-wSybkVtNApisWMwHaLH?rs=1&pid=ImgDetMain"
    val berserk = "https://th.bing.com/th/id/OIP.T-OD2pGl9-W6uLnY0D_jKQHaKe?rs=1&pid=ImgDetMain"
    val  vinland = "https://th.bing.com/th/id/OIP.oot8TxRDQtGdbkNHi3E-pwHaKb?rs=1&pid=ImgDetMain"
    val haikyuu = "https://th.bing.com/th/id/OIP.uTpLPml-BwKfT6EV1RtZsQHaK9?rs=1&pid=ImgDetMain"
    val deathNote = "https://th.bing.com/th/id/OIP.ugzcbjSxJc5XeUUDXYkH1wHaK5?rs=1&pid=ImgDetMain"
    val aot = "https://th.bing.com/th/id/OIP.TfR7XD-Y6UZDt-uLfGzu0AHaKe?rs=1&pid=ImgDetMain"
    val listOfMBook = listOf(MBook("234","One piece", author = "ichiro oda",
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
    val userName = if (FirebaseAuth.getInstance().currentUser?.email?.isNotEmpty() == true){
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0).toString()
    } else {
        "N/A"
    }
    val db = Firebase.firestore
    val collectionRef = db.collection("users")
    val fieldToRetrieve = "avatarUrl"  // Name of the field you want to get
    val conditionField = "display_name"          // Name of the field to check for "Example"
    val fieldValue = remember{
        mutableStateOf("")
    }
    val query = collectionRef
        .whereEqualTo(conditionField, userName)

    query.get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                fieldValue.value =
                    document.getString(fieldToRetrieve).toString() // No need for select
                // Use the retrieved field value as needed
                Log.d("ImageUrlSuccess", "MainContent: ${fieldValue.value}")
            }
        }
        .addOnFailureListener { exception ->
            // Handle errors
            Log.d("ImageUrlFailure", "MainContent: $exception")
        }


    Column(horizontalAlignment = Alignment.Start) {
      Row(horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
              .fillMaxWidth()
              .padding(7.dp)
              .height(80.dp),
          verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Your reading \nActivity right now...", fontSize = 20.sp)
          Column (Modifier.size(80.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center){
              if (fieldValue.value.isNotEmpty()){
                  Image(
                      painter = rememberImagePainter(data = fieldValue.value),
                      contentDescription = "user", modifier = Modifier.size(40.dp)
                          .clip(shape = CircleShape)
                  )
              }
              else{
                  Icon(
                      imageVector = Icons.Default.AccountCircle,
                      contentDescription = "user", modifier = Modifier.size(40.dp)
                  )
              }
              Text(text =userName, overflow = TextOverflow.Clip, fontSize = 14.sp,
                  textAlign = TextAlign.Center)
          }
      }
        ListCard()
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Reading List", fontSize = 20.sp)
        }
        ReadingList(listOfMBook)
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopBar(
    viewModel: LoginViewModel, navController: NavHostController,
    title: String,
    image: @Composable () -> Unit
) {
    Card(elevation = CardDefaults.elevatedCardElevation(7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(7.dp)){
        TopAppBar(title = { Text(text = title) },
            navigationIcon = {
                             image()
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White),
            actions = {
                IconButton(onClick = {
                    viewModel.userSignOut()
                    navController.navigate(ReaderScreens.Login.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout Button",
                                tint = Color.Blue
                    )
                }
            })
    }
}

@Composable
fun FAB(onClick : () -> Unit) {
   Card(elevation = CardDefaults.elevatedCardElevation(5.dp),
       colors = CardDefaults.cardColors(containerColor = Color.White),
       shape = CircleShape) {
        IconButton(onClick = { onClick()}) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Button",
                tint = Color.Blue,
                modifier = Modifier.background(Color.White.copy(0.6f))
            )
        }
    }
}
