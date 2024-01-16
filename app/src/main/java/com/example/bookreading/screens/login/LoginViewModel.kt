package com.example.bookreading.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loading = MutableLiveData(false)

    private val auth : FirebaseAuth = Firebase.auth

    private val fireStore = FirebaseFirestore.getInstance()

    private val collectionRef = fireStore.collection("users")

   private fun check(email: String?, content : () -> Unit, elseContent: () -> Unit) {
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
        val user = mutableMapOf<String,Any>()
        user["user_Id"] = userId.toString()
        user["display_name"] = displayName.toString()
        fireStore.collection("users").add(user)
    }

    private val loading : LiveData<Boolean> =_loading
     fun createAccount(
         email: String,
         password: String,
         content: (bool : Boolean) -> Unit,
         onExist: () -> Unit
     ){
       viewModelScope.launch {

          if (_loading.value == false) {
              _loading.value = true
              check(email = email.split("@")[0],content = {
                  auth.createUserWithEmailAndPassword(email, password)
                      .addOnCompleteListener {
                          if (it.isSuccessful) {
                              val displayName = it.result.user?.email?.split("@")?.get(0)
                              createUser(displayName)
                              content(_loading.value!!)
                              Log.d("SignIn", "successful signIn: ${it.result}")
                          } else {
                              Log.d("SignIn", "signIn: ${it.result}")
                          }
                          _loading.value = false
                      }
              }) { onExist() }
          }
       }
     }
    fun signIn(email: String,password: String,content : () -> Unit){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("CreateAccount", "Account Created: ${it.result}")
                        content()
                    } else {
                        Log.d("CreateAccount", "createAccount: ${it.result}")
                    }
                }
        }

}