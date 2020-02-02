package com.example.litechatter.database

data class PrivateChatRoom (
    val user1: String? = null,
    val user2: String? = null,
    val messages: List<ChatMessage>? = listOf()
)