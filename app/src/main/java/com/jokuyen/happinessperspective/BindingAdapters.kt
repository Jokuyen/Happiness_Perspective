package com.jokuyen.happinessperspective

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jokuyen.happinessperspective.database.Entry

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Entry>?) {
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}