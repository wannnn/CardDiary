package com.claire.carddiary.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.claire.carddiary.CardApplication
import com.claire.carddiary.R
import com.claire.carddiary.base.DataBindingViewHolder
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.utils.LongClickCallback
import com.claire.carddiary.utils.click

class CardAdapter : PagingDataAdapter<Card, DataBindingViewHolder<Card>>(DiffCallback()) {

    var clickListener: (card: Card) -> Unit = {}
    var longClickListener: (card: Card) -> Unit = {}

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.cardId == newItem.cardId

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int =
        if (CardApplication.isSingleRaw) R.layout.item_card_large else R.layout.item_card


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Card> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<Card>, position: Int) {
        holder.bind(getItem(position) ?: return)

        holder.itemView.click {
            clickListener.invoke(getItem(position) ?: Card())
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.invoke(getItem(position) ?: Card())
            true
        }
    }
}