package com.claire.carddiary.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.claire.carddiary.utils.ClickCallback

abstract class DataBindingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    var listener: ClickCallback = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.itemView.tag = position
        holder.bind(getItem(position))
    }
}