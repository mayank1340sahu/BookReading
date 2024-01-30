package com.example.bookreading.screens.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BookDetails(navController: NavHostController,
                bookId : String,
                ) {
Text(text = bookId)
}