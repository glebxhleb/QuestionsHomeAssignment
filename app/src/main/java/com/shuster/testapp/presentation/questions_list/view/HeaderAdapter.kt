package com.shuster.testapp.presentation.questions_list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shuster.testapp.databinding.ItemHeaderBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
        return HeaderHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1

    class HeaderHolder(binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root)
}