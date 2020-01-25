package com.example.happinessperspective

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.details.RecyclerViewAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Entry>?) {
    val adapter = recyclerView.adapter as RecyclerViewAdapter
    adapter.submitList(data)
}