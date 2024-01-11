package com.example.bookreading.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bookreading.screens.ReaderLogo

@Preview
@Composable
fun ReaderLoginScreen(
    navController: NavHostController = NavHostController(context = LocalContext.current)) {
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        ReaderLogo()
        UserForm(loading = false, isCreatedAccount = false) { email, pass ->
            Log.d("user Form", "ReaderLoginScreen: $email,$pass")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading : Boolean = false,
    isCreatedAccount : Boolean = false,
    onDone : (String,String) -> Unit = { _, _ ->}
) {
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
    Column(Modifier.fillMaxWidth().heightIn(0.dp,900.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        EmailInput(emailState = email, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })
        PasswordInput(passwordState = password,
            modifier = modifier.focusRequester(passwordFocusRequest),
            labelId = "password",
            enabled = true, //Todo Change this
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
            }
        )
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
    InputField(valueState = emailState,
        labelId = labelId,
        enable = enable,
        imeAction = imeAction,
        onAction = onAction,
        modifier = modifier
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier : Modifier = Modifier,
    valueState : MutableState<String>,
    labelId : String,
    enable : Boolean,
    isSingleLine : Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction : ImeAction,
   onAction: KeyboardActions = KeyboardActions.Default
) {
OutlinedTextField(value = valueState.value,
    onValueChange = { valueState.value = it },
    label = { Text(text = labelId)},
    singleLine = isSingleLine,
    textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
    modifier = modifier
        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        .fillMaxWidth(),
        enabled = enable,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    keyboardActions = onAction,
)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    modifier: Modifier,
    labelId: String,
    enabled: Boolean,
    imeAction: ImeAction = ImeAction.Done,
    passwordVisibility: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation = if(passwordVisibility.value) VisualTransformation.None
    else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(value = passwordState.value,
        onValueChange = {passwordState.value = it},
        enabled = enabled,
        label = { Text(text = labelId)},
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Text),
        singleLine = true,
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)}
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }){
       Icons.Default.Close
    }
}
