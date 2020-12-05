package com.claire.carddiary.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.R
import com.claire.carddiary.databinding.ItemLoadStateBinding
import com.claire.carddiary.utils.click
import com.claire.carddiary.utils.gone
import com.claire.carddiary.utils.visible

class CardLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CardLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {

        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context))
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun getStateViewType(loadState: LoadState): Int = R.layout.item_load_state


    class LoadStateViewHolder(
        val binding: ItemLoadStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retry.click { retry.invoke() }
        }

        fun bind(loadState: LoadState) {

            binding.progress.isVisible = loadState is LoadState.Loading
            binding.retry.isVisible = loadState is LoadState.Error
            binding.end.isVisible = loadState.endOfPaginationReached
        }
    }
}
