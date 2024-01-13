package com.example.bookreading.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookreading.navigation.ReaderScreens
import com.example.bookreading.screens.ReaderLogo
import com.example.bookreading.widgt.EmailInput
import com.example.bookreading.widgt.PasswordInput
import com.example.bookreading.widgt.SubmitButton

@Preview
@Composable
fun ReaderLoginScreen(
    navController: NavHostController = NavHostController(context = LocalContext.current),
    viewModel: LoginViewModel = hiltViewModel()) {
    Column(Modifier,
        horizontalAlignment = Alignment.CenterHorizontally){
        ReaderLogo()
        UserForm(loading = false, isCreatedAccount = true) { email, pass,_ ->
            viewModel.signIn(email,pass){
                navController.navigate(ReaderScreens.Home.name)
            }
            Log.d("user Form", "ReaderLoginScreen: $email,$pass")
        }
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)){
            Text(text = "New User?",
                Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.bodyLarge)
            TextButton(onClick = {navController.navigate(ReaderScreens.CreateAccount.name)}) {
                Text(text = "Create Account",
                    style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading : Boolean = false,
    isCreatedAccount : Boolean = false,
    onDone : (String,String,String) -> Unit = { _, _ ,_ ->}
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

    val valid = rememberSaveable(email.value,password.value,confPassword.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() &&
                confPassword.value.trim().isNotEmpty() && password.value == confPassword.value
    }
    val modifier = Modifier
        .height(34.dp)
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colorScheme.background)
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 900.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        EmailInput(emailState = email)
        PasswordInput(passwordState = password,
            Modifier,
            labelId = "password",
            enabled = true, //Todo Change this
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
            }
        )
        if (!isCreatedAccount) {
            PasswordInput(passwordState = confPassword,
                Modifier,
                labelId = "password",
                enabled = true, //Todo Change this
                passwordVisibility = passwordVisibility,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    if (password==confPassword)
                    { onDone(email.value.trim(),password.value.trim(),
                        confPassword.value.trim()) }
                    keyBoardController?.hide()
                }
            )
        }
            SubmitButton(
                onClick = { onDone(email.value.trim(), password.value.trim(),
                    confPassword.value.trim()) },
                textId = if (isCreatedAccount) "Login" else "SignUp",
                isValid = valid
            )
    }
}
