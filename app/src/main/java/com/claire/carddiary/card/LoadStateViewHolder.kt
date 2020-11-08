package com.claire.carddiary.card

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.databinding.ItemLoadStateBinding
import com.claire.carddiary.utils.click

class LoadStateViewHolder(
    val binding: ItemLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retry.click { retry.invoke() }
    }

    fun bind(loadState: LoadState) {

        binding.progress.isVisible = loadState is LoadState.Loading
        binding.retry.isVisible = loadState !is LoadState.Loading
    }
}