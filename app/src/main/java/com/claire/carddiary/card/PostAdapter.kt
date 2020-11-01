package com.claire.carddiary.card

import androidx.recyclerview.widget.DiffUtil
import com.claire.carddiary.R
import com.claire.carddiary.base.DataBindingAdapter
import com.claire.carddiary.base.DataBindingViewHolder
import com.claire.carddiary.data.model.Post

class PostAdapter: DataBindingAdapter<Post>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            true

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
            false

        override fun getChangePayload(oldItem: Post, newItem: Post): Any? {
            return newItem.progress
        }
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<Post>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bindWithPayload(payloads[0] as Int)
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_posting

}