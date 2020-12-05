package com.claire.carddiary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.claire.carddiary.utils.click
import kotlinx.android.extensions.LayoutContainer

class ScrollItemViewHolder(
    private val parent: ViewGroup,
    @LayoutRes layoutId: Int
) : LayoutContainer {

    private var binding: ViewDataBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), layoutId, parent, false
    )

    override val containerView: View
        get() = binding.root

    fun bind(item: Any): ScrollItemViewHolder {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        return this
    }

    fun setOnClickListener(onClick: (View) -> Unit): ScrollItemViewHolder {
        containerView.click {
            onClick(it)
        }
        return this
    }

    fun addViewToParent() {
        parent.addView(containerView)
    }
}