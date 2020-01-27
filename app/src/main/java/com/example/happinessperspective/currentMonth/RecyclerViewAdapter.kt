package com.example.happinessperspective.currentMonth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.databinding.EntryViewHolderBinding

class RecyclerViewAdapter(private val onClickListener: OnClickListener) : ListAdapter<Entry, RecyclerViewAdapter.EntryViewHolder>(DiffCallback) {

    class EntryViewHolder(private var binding: EntryViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            binding.entry = entry
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.entryId == newItem.entryId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        return EntryViewHolder(EntryViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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