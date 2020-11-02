package com.claire.carddiary.edit

import androidx.recyclerview.widget.DiffUtil
import com.claire.carddiary.R
import com.claire.carddiary.base.DataBindingAdapter

class ImagePagerAdapter: DataBindingAdapter<String>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_image

}