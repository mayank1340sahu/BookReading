package com.example.bookreading.screens.login.widgt

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    trailingIcon :@Composable ()->Unit = {}
) {
    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        enabled = enable,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

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
    InputField(valueState = passwordState,
        labelId = labelId, enable = enabled,
        imeAction = imeAction,
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) },
        onAction = onAction)
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }){
        Icon(imageVector = Icons.Default.Info,
            contentDescription ="")
    }
}

@Composable
fun SubmitButton (onClick : () ->Unit,
                  textId : String,
                  isValid : Boolean
) {
    Button(onClick = onClick, enabled = isValid) {
        Text(text = textId)
    }
}
