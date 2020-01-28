package com.example.litechatter.screens.chats


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.R
import com.example.litechatter.database.ChatItem
import com.example.litechatter.databinding.FragmentChatsBinding
import timber.log.Timber

class ChatsFragment : Fragment() {
    private lateinit var chatsRecycler: RecyclerView
    private lateinit var chatsManager: RecyclerView.LayoutManager

    private val chatsDataSet: MutableList<ChatItem> = mutableListOf(ChatItem("1", "Musab Khan", "You: How are you???"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentChatsBinding>(inflater,
            R.layout.fragment_chats, container, false)

        chatsManager = LinearLayoutManager(this.context)
        val chatsAdapter = ChatsAdapter(ChatsListener {
            Timber.i("chatitem clicked")

            val intent = Intent(context, FullChatActivity::class.java).apply {
                putExtra("123", "123")
            }
            startActivity(intent)

        })

        chatsAdapter.submitList(chatsDataSet)

        chatsRecycler = binding.chatsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = chatsManager
            adapter = chatsAdapter
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}