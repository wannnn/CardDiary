package com.claire.carddiary.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.claire.carddiary.utils.ClickCallback
import com.claire.carddiary.utils.LongClickCallback
import com.claire.carddiary.utils.click

abstract class DataBindingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    var clickListener: ClickCallback = {}
    var longClickListener: LongClickCallback = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.click {
            clickListener.invoke(position)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.invoke(holder.absoluteAdapterPosition)
            false
        }
    }
}