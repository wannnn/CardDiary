package com.claire.carddiary.card

import androidx.recyclerview.widget.DiffUtil
import com.claire.carddiary.R
import com.claire.carddiary.base.DataBindingAdapter
import com.claire.carddiary.data.Card

class CardAdapter : DataBindingAdapter<Card>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.id == newItem.id
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_card

}