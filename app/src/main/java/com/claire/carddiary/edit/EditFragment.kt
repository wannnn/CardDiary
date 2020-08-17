package com.claire.carddiary.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.claire.carddiary.databinding.FragEditBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditFragment : Fragment() {

    private lateinit var binding: FragEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImagePagerAdapter(List(3) {""}).apply {
            listener = {
                Toast.makeText(context, "open gallery", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imagePager.adapter = adapter

        TabLayoutMediator(binding.indicator, binding.imagePager) { _, _ -> }.attach()

    }
}