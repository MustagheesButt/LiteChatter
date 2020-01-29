package com.example.litechatter.database

import com.google.firebase.firestore.DocumentId

data class User (
    var id: String? = null,
    var userName: String? = null,
    var contacts: HashMap<String, String>? = null
)