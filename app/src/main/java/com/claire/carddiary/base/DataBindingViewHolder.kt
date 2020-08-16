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

    fun bind(item: T, position: Int) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        binding.root.click {
            listener.invoke(position)
        }
    }
}