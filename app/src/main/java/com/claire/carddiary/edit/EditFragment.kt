package com.claire.carddiary.edit

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.claire.carddiary.R
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.databinding.FragEditBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class EditFragment : Fragment() {

    private val vm: EditViewModel by activityViewModels { ViewModelFactory() }
    private lateinit var binding: FragEditBinding
    private val adapter: ImagePagerAdapter by lazy { ImagePagerAdapter() }

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
        observeViewModel()

        binding.vm = vm

        with(adapter) {
            listener = { openGallery() }
        }

        binding.imagePager.adapter = adapter

        TabLayoutMediator(binding.indicator, binding.imagePager) { _, _ -> }.attach()

    }

    private fun observeViewModel() {
        vm.card.observe(viewLifecycleOwner) {
            binding.item = it
            adapter.updateData(it.images)
        }

        vm.alertMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        vm.openPicker.observe(viewLifecycleOwner) {
            openDatePicker()
        }
    }

    private fun openGallery() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
                    if (uris.isEmpty().not()) {
                        val list = uris.map { it.toString() }
                        vm.setImages(list)
                    }
                }.launch("image/*")
            } else {
                Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }.launch(READ_EXTERNAL_STORAGE)
    }

    private fun openDatePicker() {
        val calender = Calendar.getInstance()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calender.set(year, month, day)
            vm.setDate(calender.time)
        }

        DatePickerDialog(requireContext(),
            dateListener,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)).show()
    }

}