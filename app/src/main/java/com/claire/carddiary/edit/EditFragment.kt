package com.claire.carddiary.edit

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.claire.carddiary.R
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.databinding.FragEditBinding
import com.claire.carddiary.utils.hideKeyboard
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class EditFragment : Fragment() {

    private val binding: FragEditBinding by FragmentBindingProvider(R.layout.frag_edit)
    private val vm: EditViewModel by viewModels()
    private val adapter: ImagePagerAdapter by lazy { ImagePagerAdapter() }
    private val args: EditFragmentArgs by navArgs()

    private lateinit var checkPermission: ActivityResultLauncher<String>
    private lateinit var pickImage: ActivityResultLauncher<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        checkPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickImage.launch("image/*")
            } else {
                Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
        pickImage = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isEmpty().not()) {
                val list = uris.map { it.toString() }
                vm.setImages(list)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm

        observeData()

        vm.setScriptCard(args.item)

        with(binding.toolbar) {
            setupWithNavController(binding.toolbar, findNavController())
            setNavigationIcon(R.drawable.ic_arrow_back_20)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.check -> {  // save or update data
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(getString(R.string.nav_key_card), vm.getCard().value)
                        findNavController().navigateUp()
                    }
                }
                true
            }
        }

        with(adapter) {
            clickListener = { openGallery() }

            longClickListener = { position ->
                if (vm.hasImages(position)) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(getString(R.string.delete_photo_hilt))
                        .setPositiveButton(getString(R.string.sure)) { _, _ ->
                            vm.deleteImage(position)
                        }
                        .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                        .show()
                }
            }
        }

        binding.listener = { openDatePicker() }

        binding.imagePager.adapter = adapter

        TabLayoutMediator(binding.indicator, binding.imagePager) { _, _ -> }.attach()

    }

    private fun observeData() {

        vm.getCard().observe(viewLifecycleOwner) {
            binding.item = it
            adapter.submitList(it.images.toList())
        }

        vm.alertMsg.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

    private fun openGallery() {
        checkPermission.launch(READ_EXTERNAL_STORAGE)
    }

    private fun openDatePicker() {
        hideKeyboard()
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