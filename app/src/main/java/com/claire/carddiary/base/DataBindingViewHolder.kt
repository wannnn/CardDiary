package com.claire.carddiary.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.utils.ClickCallback
import com.claire.carddiary.utils.LongClickCallback
import com.claire.carddiary.utils.click

class DataBindingViewHolder<T>(
    private val binding: ViewDataBinding,
    private var click: ClickCallback,
    private var longClick: LongClickCallback = {}
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.click {
            click.invoke(itemView.tag as Int)
        }
        binding.root.setOnLongClickListener {
            longClick.invoke(itemView.tag as Int)
            true
        }
    }

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}