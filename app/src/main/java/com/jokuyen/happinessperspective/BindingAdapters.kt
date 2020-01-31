package com.jokuyen.happinessperspective

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.currentMonth.RecyclerViewAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Entry>?) {
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data)
}