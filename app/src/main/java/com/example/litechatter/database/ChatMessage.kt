package com.example.litechatter.database

import com.google.firebase.Timestamp
import java.util.*

data class ChatMessage (
    val sender: String? = null,
    val msg: String? = null,
    val datetime: Timestamp = Timestamp(Date())
)