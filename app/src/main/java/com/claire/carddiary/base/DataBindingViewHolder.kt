package com.claire.carddiary.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.utils.ClickCallback
import com.claire.carddiary.utils.click

class DataBindingViewHolder<T>(
    private val binding: ViewDataBinding,
    private var listener: ClickCallback
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.click {
            listener.invoke(itemView.tag as Int)
        }
    }

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}