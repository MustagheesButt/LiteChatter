package com.example.litechatter.screens.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.database.ChatMessage
import com.example.litechatter.databinding.ChatmessageListitemViewBinding

class PrivateChatRoomAdapter(val clickListener: ChatMessageListener): ListAdapter<ChatMessage, PrivateChatRoomAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: ChatmessageListitemViewBinding): RecyclerView.ViewHolder(binding.root) {
        //private val res = itemView.resources

        fun bind(item: ChatMessage, clickListener: ChatMessageListener) {
            binding.chatMessage = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatmessageListitemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
    override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem.msg == newItem.msg
    }

    override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
        return oldItem == newItem
    }
}

class ChatMessageListener(val clickHandler: (chatRoomId: String) -> Unit) {
    fun onClick(chatMessage: ChatMessage) = clickHandler(chatMessage.msg.toString())
}