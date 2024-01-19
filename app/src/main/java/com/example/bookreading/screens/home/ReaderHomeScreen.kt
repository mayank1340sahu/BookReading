package com.example.bookreading.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookreading.R
import com.example.bookreading.navigation.ReaderScreens
import com.example.bookreading.screens.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderHomeScreen(navController: NavHostController,
                     viewModel: LoginViewModel = hiltViewModel()) {
   Scaffold (topBar = { ReaderTopBar(viewModel,navController)},floatingActionButton = {FAB()}){
       Column(Modifier.padding(it)) {
    MainContent()
            }
       }
   }

@Composable
fun MainContent() {
    val userName = if (FirebaseAuth.getInstance().currentUser?.email?.isNotEmpty() == true){
        FirebaseAuth.getInstance().currentUser?.displayName } else {
        "N/A"
    }
    Column {
      Row(horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "Your reading \nActivity right now...")
          Column {
              Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "user")
              if (userName != null) {
                  Text(text =userName )
              }
          }
      }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderTopBar(viewModel: LoginViewModel,navController: NavHostController) {
    Card(elevation = CardDefaults.elevatedCardElevation(7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(7.dp)){
        TopAppBar(title = { Text(text = "Book Reading") },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.book),
                    contentDescription = "AppIcon",
                    modifier = Modifier.size(30.dp)
                )
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
fun FAB() {
   Card(elevation = CardDefaults.elevatedCardElevation(5.dp),
       colors = CardDefaults.cardColors(containerColor = Color.White),
       shape = CircleShape) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Button",
                tint = Color.Blue,
                modifier = Modifier.background(Color.White.copy(0.6f))
            )
        }
    }
}
