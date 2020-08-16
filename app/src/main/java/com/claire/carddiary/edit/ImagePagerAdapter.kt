package com.claire.carddiary.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.claire.carddiary.base.DataBindingViewHolder
import com.claire.carddiary.databinding.ItemImageBinding
import com.claire.carddiary.utils.ClickCallback

class ImagePagerAdapter(
    private val images: List<String>
): RecyclerView.Adapter<DataBindingViewHolder<String>>() {

    var listener: ClickCallback = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<String> {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataBindingViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: DataBindingViewHolder<String>, position: Int)
            = holder.bind(images[position], position)
}