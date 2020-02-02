package com.example.litechatter.screens.chatroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.litechatter.R
import com.example.litechatter.database.ChatMessage
import com.example.litechatter.databinding.ActivityPrivateChatRoomBinding
import timber.log.Timber

class PrivateChatRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPrivateChatRoomBinding>(this, R.layout.activity_private_chat_room)

        // get userId of with whom current user is chatting with
        val chatRoomId = intent.getStringExtra("chatRoomId")

        val viewModelFactory = ChatRoomViewModelFactory(chatRoomId!!)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatRoomViewModel::class.java)

        val privateChatRoomAdapter = PrivateChatRoomAdapter(ChatMessageListener {
            Timber.i("user clicked: $it")
        })

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            title = chatRoomId
        }

        viewModel.privateChatRoom.observe(this, Observer {privateChatRoom ->
            if (privateChatRoom.user1 == viewModel.currentUser?.uid) {
                supportActionBar?.let {
                    title = privateChatRoom.user2
                }
            } else {
                supportActionBar?.let {
                    title = privateChatRoom.user1
                }
            }

            privateChatRoomAdapter.submitList(privateChatRoom.messages)
        })

        binding.privateChatRoomRV.adapter = privateChatRoomAdapter

        binding.sendMsgBtn.setOnClickListener {
            val msg = ChatMessage(
                viewModel.currentUser?.displayName!!,
                binding.msgTextBox.text.toString()
            )

            binding.msgTextBox.setText("")
        }
    }
}
