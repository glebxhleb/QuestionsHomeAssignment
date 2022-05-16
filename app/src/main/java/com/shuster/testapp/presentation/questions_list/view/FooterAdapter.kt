package com.shuster.testapp.presentation.questions_list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shuster.testapp.databinding.ItemFooterBinding
import com.shuster.testapp.databinding.ItemHeaderBinding

class FooterAdapter(
    private val onSubmitClick: () -> Unit
) : RecyclerView.Adapter<FooterAdapter.FooterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FooterHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFooterBinding.inflate(layoutInflater, parent, false)
        return FooterHolder(binding, onSubmitClick)
    }

    override fun onBindViewHolder(holder: FooterHolder, position: Int) {
    }

    override fun getItemCount(): Int = 1

    class FooterHolder(binding: ItemFooterBinding, onSubmitClick: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnSubmit.setOnClickListener { onSubmitClick() }
        }
    }
}