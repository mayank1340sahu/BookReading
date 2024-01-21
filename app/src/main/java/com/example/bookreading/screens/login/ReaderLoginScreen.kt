package com.example.bookreading.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookreading.navigation.ReaderScreens
import com.example.bookreading.screens.ReaderLogo
import com.example.bookreading.screens.login.widgt.EmailInput
import com.example.bookreading.screens.login.widgt.PasswordInput
import com.example.bookreading.screens.login.widgt.SubmitButton

@Preview
@Composable
fun ReaderLoginScreen(
    navController: NavHostController = NavHostController(context = LocalContext.current),
    viewModel: LoginViewModel = hiltViewModel()) {
    val loading = remember{
        mutableStateOf(false)
    }
    val notExist = remember{
        mutableStateOf(false)
    }
    val passwordError = remember{
        mutableStateOf(false)
    }
    if (!loading.value){
        Column(
            Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderLogo()
            UserForm(
                alreadyExist = notExist.value, isCreatedAccount = true,
                passwordError = false,
                emailError = false,
                showError = passwordError.value
            ) { email, pass, _ ->
                viewModel.signIn(email, pass, content = {
                    navController.navigate(ReaderScreens.Home.name)
                }, loading = {
                    loading.value = it
                }, passwordError = {
                    passwordError.value = it
                }) {
                    notExist.value = true
                    loading.value = false
                }
                Log.d("user Form", "ReaderLoginScreen: $email,$pass")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "New User?",
                    Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                TextButton(onClick = { navController.navigate(ReaderScreens.CreateAccount.name) }) {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
    else {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize())
        { CircularProgressIndicator(modifier = Modifier.size(300.dp)) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    alreadyExist: Boolean,
    isCreatedAccount: Boolean = false,
    passwordError: Boolean,
    emailError: Boolean,
    showError: Boolean,
    onDone: (String, String, String) -> Unit = { _, _, _ -> },


    ) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val confPassword = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    //val passwordFocusRequest = FocusRequester

    val keyBoardController = LocalSoftwareKeyboardController.current

    val valid = if (!isCreatedAccount){
        rememberSaveable(email.value, password.value, confPassword.value) {
            email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() &&
                    confPassword.value.trim().isNotEmpty()
        }
    } else {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 900.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        EmailInput(emailState = email)
        if (emailError)
        { Text(text = "invalid email", color = Color.Red) }
        PasswordInput(passwordState = password,
            Modifier,
            labelId = "password",
            enabled = true,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
            }
        )
        if (passwordError)
        { Text(text = "please enter at least 6 character", color = Color.Red) }
        if (!isCreatedAccount) {
            PasswordInput(passwordState = confPassword,
                Modifier,
                labelId = "password",
                enabled = true,
                passwordVisibility = passwordVisibility,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    if (password==confPassword)
                    { onDone(email.value.trim(),password.value.trim(),
                        confPassword.value.trim()) }
                    keyBoardController?.hide()
                }
            )
            if (showError)
            { Text(text = "password didn't match", color = Color.Red) }

        }
            SubmitButton(
                onClick = { onDone(email.value.trim(), password.value.trim(),
                    confPassword.value.trim()) },
                textId = if (isCreatedAccount) "Login" else "SignUp",
                isValid = valid
            )
        if (alreadyExist){

            if (!isCreatedAccount){
                Text(
                    text = "Account already exist,please login!",
                    modifier = Modifier.padding(top = 10.dp),
                    color = Color.Red
                )
            }
            else{
                Text(text = "Account does not exist, please Create Account!",
                    modifier = Modifier.padding(top = 10.dp), color = Color.Red)
            }
        }

    }
}
