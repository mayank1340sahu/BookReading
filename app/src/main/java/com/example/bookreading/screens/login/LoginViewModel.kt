package com.example.bookreading.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loading = MutableLiveData(false)

    private val auth : FirebaseAuth = Firebase.auth

    val loading : LiveData<Boolean> =_loading
     fun signIn(email : String , password : String,content : () -> Unit){
       viewModelScope.launch {
           try{
               auth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener {
                       if (it.isSuccessful) {
                           content()
                           Log.d("SignIn", "successful signIn: ${it.result.toString()}")
                       } else {
                           Log.d("SignIn", "signIn: ${it.result.toString()}")
                       }
                   }
           }
           catch (ex : Exception){
               Log.d("SignInError", "signIn: $ex")
           }
       }
     }
    fun createAccount(email: String,password: String,content : () -> Unit){
        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("CreateAccount", "Account Created: ${it.result.toString()}")
                        content()
                    } else {
                        Log.d("CreateAccount", "createAccount: ${it.result.toString()}")
                    }
                }
        }
        catch (e : Exception){
            Log.d("CreateAccountError", "createAccount: ${e.message}")
        }
    }
}