package com.example.litechatter.screens.chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.litechatter.R
import com.example.litechatter.databinding.FragmentFullChatBinding
import com.example.litechatter.helpers.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FullChatActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<FragmentFullChatBinding>(this, R.layout.fragment_full_chat)

        binding.sendMsgBtn.setOnClickListener {
            val msg = ChatMessage(currentUser?.displayName!!, binding.msgTextBox.text.toString())

            binding.msgTextBox.setText("")
        }
    }
}
