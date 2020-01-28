

package com.example.litechatter.screens.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.database.ChatItem
import com.example.litechatter.database.User
import com.example.litechatter.databinding.ChatsListitemViewBinding
import com.example.litechatter.databinding.ContactsListitemViewBinding

class ChatsAdapter(val clickListener: ChatsListener): ListAdapter<ChatItem, ChatsAdapter.ViewHolder>(ChatsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: ChatsListitemViewBinding): RecyclerView.ViewHolder(binding.root) {
        private val res = itemView.resources

        fun bind(item: ChatItem, clickListener: ChatsListener) {
            binding.chatItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChatsListitemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ChatsDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
    override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem.chatId == newItem.chatId
    }

    override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
        return oldItem == newItem
    }
}

class ChatsListener(val clickHandler: (chatItem: ChatItem) -> Unit) {
    fun onClick(chatItem: ChatItem) = clickHandler(chatItem)
}