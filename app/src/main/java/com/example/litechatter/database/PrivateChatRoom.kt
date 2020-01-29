package com.example.litechatter.database

data class PrivateChatRoom (
    val user1: String,
    val user2: String,
    val messages: List<ChatMessage> = listOf()
)