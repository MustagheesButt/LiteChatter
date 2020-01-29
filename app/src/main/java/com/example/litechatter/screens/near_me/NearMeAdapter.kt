

package com.example.litechatter.screens.near_me

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.litechatter.database.User
import com.example.litechatter.databinding.NearMeListitemViewBinding

class NearMeAdapter(val clickListener: NearMeListener): ListAdapter<User, NearMeAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (val binding: NearMeListitemViewBinding): RecyclerView.ViewHolder(binding.root) {
        private val res = itemView.resources

        fun bind(item: User, clickListener: NearMeListener) {
            binding.user = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NearMeListitemViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class NearMeListener(val clickHandler: (userId: String) -> Unit) {
    fun onClick(user: User) = clickHandler(user.id!!)
}