package com.example.bookreading.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReaderLoginScreen(navController: NavHostController) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }
    val passwordFocusRequest = FocusRequester.Default

    val keyBoardController = LocalSoftwareKeyboardController.current

    val valid = rememberSaveable(email.value,password.value) {
         email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
     }
    val modifier = Modifier
        .height(34.dp)
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colorScheme.background)
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){

    }
}

@Composable
fun EmailInput(modifier : Modifier = Modifier,
               emailState : MutableState<String>,
               labelId : String = "Email",
               enable : Boolean = true,
               imeAction : ImeAction = ImeAction.Next,
               onAction : KeyboardActions = KeyboardActions.Default
               ) {
    inputField()
}

fun inputField() {

}
