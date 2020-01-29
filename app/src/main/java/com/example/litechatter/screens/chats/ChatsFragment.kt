package com.example.litechatter.screens.chats


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.screens.chatroom.PrivateChatRoomActivity
import com.example.litechatter.R
import com.example.litechatter.database.ChatItem
import com.example.litechatter.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {
    private lateinit var chatsRecycler: RecyclerView
    private lateinit var chatsManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentChatsBinding>(inflater, R.layout.fragment_chats, container, false)
        val viewModel = ViewModelProviders.of(this).get(ChatsViewModel::class.java)
        chatsManager = LinearLayoutManager(this.context)

        val chatsAdapter = ChatsAdapter(ChatsListener {chatItem ->
            val intent = Intent(context, PrivateChatRoomActivity::class.java).apply {
                putExtra("chattingWith", chatItem.chatRoomId)
            }
            startActivity(intent)

        })

        viewModel.recentChatMessages.observe(this, Observer {
            chatsAdapter.submitList(it)
        })

        chatsRecycler = binding.chatsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = chatsManager
            adapter = chatsAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}