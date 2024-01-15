package com.example.bookreading.screens.login

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loading = MutableLiveData(false)

    private val auth : FirebaseAuth = Firebase.auth

    private val userId = auth.currentUser?.uid

    private val fireStore = FirebaseFirestore.getInstance()
    fun createUser(userId : String , displayName : String){

    }

    val loading : LiveData<Boolean> =_loading
     fun signIn(email : String , password : String,content : () -> Unit){
       viewModelScope.launch {
           try{
               auth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener {
                       if (it.isSuccessful) {
                           content()
                           Log.d("SignIn", "successful signIn: ${it.result}")
                       } else {
                           Log.d("SignIn", "signIn: ${it.result}")
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
                        Log.d("CreateAccount", "Account Created: ${it.result}")
                        content()
                    } else {
                        Log.d("CreateAccount", "createAccount: ${it.result}")
                    }
                }
        }
        catch (e : Exception){
            Log.d("CreateAccountError", "createAccount: ${e.message.toString()}")
        }
    }
}