package com.example.bookreading.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreading.screens.ReaderSplashScreen
import com.example.bookreading.screens.createAccount.ReaderCreateAccount
import com.example.bookreading.screens.detail.BookDetails
import com.example.bookreading.screens.home.ReaderHomeScreen
import com.example.bookreading.screens.login.ReaderLoginScreen
import com.example.bookreading.screens.search.ReaderSearchScreen
import com.example.bookreading.screens.stat.ReaderStatScreen
import com.example.bookreading.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.Splash.name ){
        composable(ReaderScreens.Splash.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.Home.name){
            ReaderHomeScreen(navController = navController)
        }
        composable(ReaderScreens.Detail.name){
            BookDetails(navController = navController)
        }
        composable(ReaderScreens.Login.name){
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.Update.name){
            ReaderUpdateScreen(navController = navController)
        }
        composable(ReaderScreens.CreateAccount.name){
            ReaderCreateAccount(navController = navController)
        }
        composable(ReaderScreens.Stat.name){
            ReaderStatScreen(navController = navController)
        }
        composable(ReaderScreens.Search.name){
            ReaderSearchScreen(navController = navController)
        }
    }
}