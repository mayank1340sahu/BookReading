package com.example.bookreading.repository

class DataOrException <T,Boolean,E:Exception>(
    var data : T? = null,
    var loading : Boolean? = null,
    var exception: java.lang.Exception? = null
)
