package com.example.bookreading.screens.createAccount

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookreading.navigation.ReaderScreens
import com.example.bookreading.screens.ReaderLogo
import com.example.bookreading.screens.login.LoginViewModel
import com.example.bookreading.screens.login.UserForm

@Preview
@Composable
fun ReaderCreateAccount(
    navController: NavHostController = NavHostController(context = LocalContext.current),
    viewModel: LoginViewModel = hiltViewModel()) {
    val confpasswordError = remember {
        mutableStateOf(false)
    }
    val passwordError = remember {
        mutableStateOf(false)
    }
    val emailError = remember {
        mutableStateOf(false)
    }
    Column(
        Modifier,
        horizontalAlignment = Alignment.CenterHorizontally){
        ReaderLogo()
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp))
        { Text(text = "Please enter a valid email and at least a 6 character password") }
        UserForm(loading = false, isCreatedAccount = false,
            passwordError = passwordError.value,
            emailError = emailError.value,
            showError = confpasswordError.value) { email, pass,conf ->
            if (email.contains("@gmail.com")){
               if (pass.indexOf(pass.last())>=5) {
                    if (pass == conf) {
                        viewModel.createAccount(email, pass) {
                            navController.navigate(ReaderScreens.Home.name)
                        }
                    } else {
                        confpasswordError.value = true
                        emailError.value = false
                        passwordError.value = false
                    }
                }
                else{
                    passwordError.value = true
                   emailError.value = false
                   confpasswordError.value = false
               }
            }
            else
            {
                passwordError.value = false
                emailError.value = true
                confpasswordError.value = false
            }

            Log.d("user Form", "ReaderLoginScreen: $email,$pass,$conf")
        }
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)){
            Text(text = "Already Account?",
                Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            TextButton(onClick = {navController.navigate(ReaderScreens.Login.name)}) {
                Text(text = "Login",
                    style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}