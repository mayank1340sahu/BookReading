package com.example.bookreading.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreading.R
import com.example.bookreading.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loading = MutableLiveData(false)

    private val auth : FirebaseAuth = Firebase.auth

    private val fireStore = FirebaseFirestore.getInstance()

     val collectionRef = fireStore.collection("users")

   fun check(email: String, content : () -> Unit, elseContent: () -> Unit) {
       Log.d("check function", "check: $email")
        collectionRef.whereEqualTo("display_name",email).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.d("alreadyNotExist", "check: ${documents.isEmpty}")
                    // Value doesn't exist
                    content()
                } else {

                    // Value exists
                    elseContent()
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
                Log.d("check", "check: $exception")
            }
    }

    private fun createUser(displayName : String?){
        val userId = auth.currentUser?.uid
        val user = UserData(
            userId = userId.toString(),
            display = displayName.toString(),
            avatarUrl = "https://firebasestorage.googleapis.com/v0/b/book-reader-91a18.appspot.com/o/MONK%20DEVELOPERS.png?alt=media&token=bf1910d0-385a-42d4-a3ae-8984670761b6", quote = "quote",
            id = null, profession = "Android Developer").toMap()
        fireStore.collection("users").add(user)
    }

     private val loading : LiveData<Boolean> =_loading
     fun createAccount(
         email: String,
         password: String,
         loading : (bool : Boolean) -> Unit,
         content: () -> Unit,
         onExist: () -> Unit
     ){
       viewModelScope.launch {

          if (_loading.value == false) {
              _loading.value = true
              loading(_loading.value!!)
              check(email = email.split("@")[0],content = {
                  auth.createUserWithEmailAndPassword(email, password)
                      .addOnCompleteListener {
                          if (it.isSuccessful) {
                              val displayName = it.result.user?.email?.split("@")?.get(0)
                              createUser(displayName)
                              content()
                              Log.d("SignIn", "successful signIn: ${it.result}")
                          } else {
                              Log.d("SignIn", "signIn: ${it.result}")
                          }
                      }
              }, elseContent = { onExist()
                  Log.d("CreateAccount Function", "createAccount: ${_loading.value}")
              })

          }
       }
     }
    fun signIn(email: String,
               password: String,
               passwordError: (Boolean) ->Unit,
               loading : (bool : Boolean) -> Unit,
               content: () -> Unit,
               onExist: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            loading(_loading.value!!)
            check(email = email.split("@")[0], content = {onExist()},
                elseContent = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("CreateAccount", "Account Created: ${it.result}")
                            content()
                        } else {
                            Log.d("CreateAccount", "createAccount: ${it.exception}")
                            passwordError(true)
                        }
                    }
            })
        }
    }
    fun userSignOut(){
         auth.signOut().apply { Log.d("Signed Out", "userSignOut: ") }
    }
}