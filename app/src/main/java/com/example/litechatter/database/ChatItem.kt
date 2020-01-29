package com.example.litechatter.database

data class ChatItem (
    var chatRoomId: String,
    var chatWith: User,
    var lastMessage: String
)