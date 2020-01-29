

package com.example.litechatter.screens.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.database.Contact
import com.example.litechatter.databinding.ContactsListitemViewBinding

class ContactsAdapter(val clickListener: ContactsListener): ListAdapter<Contact, ContactsAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: ContactsListitemViewBinding): RecyclerView.ViewHolder(binding.root) {
        private val res = itemView.resources

        fun bind(item: Contact, clickListener: ContactsListener) {
            binding.contact = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ContactsListitemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.user.id == newItem.user.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

class ContactsListener(val clickHandler: (chatRoomId: String) -> Unit) {
    fun onClick(contact: Contact) = clickHandler(contact.privateChatRoomId)
}