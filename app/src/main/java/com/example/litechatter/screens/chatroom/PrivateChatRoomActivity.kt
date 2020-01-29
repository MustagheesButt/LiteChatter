package com.example.litechatter.screens.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.litechatter.R
import com.example.litechatter.database.ChatMessage
import com.example.litechatter.databinding.ActivityPrivateChatRoomBinding

class PrivateChatRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPrivateChatRoomBinding>(this, R.layout.activity_private_chat_room)

        // get userId of with whom current user is chatting with
        val chatRoomId = intent.getStringExtra("chatRoomId")

        val viewModelFactory = ChatRoomViewModelFactory(chatRoomId)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatRoomViewModel::class.java)

        //val privateChatRoom = getPrivateChatRoomWith(chattingWithId!!)!!

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            title = chatRoomId
        }

        binding.sendMsgBtn.setOnClickListener {
            val msg = ChatMessage(
                viewModel.currentUser?.displayName!!,
                binding.msgTextBox.text.toString()
            )

            binding.msgTextBox.setText("")
        }
    }
}
