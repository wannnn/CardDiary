package com.claire.carddiary.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claire.carddiary.databinding.FragDetailBinding
import com.claire.carddiary.edit.ImagePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var binding: FragDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val adapter: ImagePagerAdapter by lazy { ImagePagerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            item = args.item
            imagePager.adapter = adapter

            listener = { findNavController().navigateUp() }
        }

        TabLayoutMediator(binding.indicator, binding.imagePager) { _, _ -> }.attach()

        adapter.submitList(args.item.images)
    }

}