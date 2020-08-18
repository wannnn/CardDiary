package com.claire.carddiary.edit

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.claire.carddiary.R
import com.claire.carddiary.card.CardViewModel
import com.claire.carddiary.databinding.FragEditBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditFragment : Fragment() {

    private val vm: CardViewModel by viewModels()
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

        binding.vm = vm

        val adapter = ImagePagerAdapter().apply {
            updateData(List(3) {""})
            listener = {
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                    if (isGranted) {
                        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
                            vm.setImages(uris)
                            Toast.makeText(context, uris[0]?.toString(), Toast.LENGTH_SHORT).show()
                        }.launch("image/*")
                    } else {
                        Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                    }
                }.launch(READ_EXTERNAL_STORAGE)
            }
        }
        binding.imagePager.adapter = adapter

        TabLayoutMediator(binding.indicator, binding.imagePager) { _, _ -> }.attach()

    }
}