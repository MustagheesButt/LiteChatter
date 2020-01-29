package com.example.litechatter.database

import com.google.firebase.Timestamp
import java.util.*

data class ChatMessage (
    val sender: String,
    val msg: String,
    val datetime: Timestamp = Timestamp(Date())
)