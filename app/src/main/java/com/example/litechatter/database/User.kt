package com.example.litechatter.database

data class User (
    var id: String? = null,
    var userName: String? = null,
    var contacts: HashMap<String, String>? = null
)