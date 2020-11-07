package com.claire.carddiary.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.R
import com.claire.carddiary.databinding.ItemLoadStateBinding
import com.claire.carddiary.utils.click

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_load_state, parent, false)
) {
    private val binding = ItemLoadStateBinding.bind(itemView)

    init {
        binding.retry.click { retry() }
    }

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            binding.error.text = loadState.error.localizedMessage
        }

        binding.loading.isVisible = loadState is LoadState.Loading
        binding.retry.isVisible = loadState is LoadState.Error
        binding.error.isVisible = loadState is LoadState.Error
    }
}