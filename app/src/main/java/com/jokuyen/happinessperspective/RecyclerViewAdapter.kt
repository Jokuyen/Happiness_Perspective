package com.jokuyen.happinessperspective

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.databinding.EntryViewHolderBinding

class RecyclerViewAdapter(private val onClickListener: OnClickListener) : ListAdapter<Entry, RecyclerViewAdapter.EntryViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }

    class EntryViewHolder(private var binding: EntryViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            binding.entry = entry
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EntryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EntryViewHolderBinding.inflate(layoutInflater, parent, false)
                return EntryViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = getItem(position)

        holder.itemView.setOnClickListener{
            onClickListener.onClick(entry)
        }

        holder.bind(entry)
    }

    class OnClickListener(val clickerListener: (entry: Entry) -> Unit) {
        fun onClick(entry: Entry) = clickerListener(entry)
    }
}