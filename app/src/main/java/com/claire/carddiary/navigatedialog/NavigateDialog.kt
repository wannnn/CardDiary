package com.claire.carddiary.navigatedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.claire.carddiary.R
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.databinding.DialogNavigateBinding
import com.claire.carddiary.utils.click
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NavigateDialog : BottomSheetDialogFragment() {

    private val binding: DialogNavigateBinding by FragmentBindingProvider(R.layout.dialog_navigate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigateEdit.click {
            findNavController().navigate(NavigateDialogDirections.toEditFragment(null))
            dismiss()
        }

        binding.navigateProfile.click {
            findNavController().navigate(NavigateDialogDirections.toProfileFragment())
            dismiss()
        }
    }
}