package com.claire.carddiary.navigatedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.claire.carddiary.R
import com.claire.carddiary.ViewModelFactory
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.databinding.DialogEditNavigateBinding
import com.claire.carddiary.utils.click
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NavigateEditDialog : BottomSheetDialogFragment() {

    private val binding: DialogEditNavigateBinding by FragmentBindingProvider(R.layout.dialog_edit_navigate)
    private val vm: NavigateEditViewModel by viewModels { ViewModelFactory() }
    private val args: NavigateEditDialogArgs by navArgs()

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
            findNavController().navigate(NavigateEditDialogDirections.toEditFragment(args.item))
            dismiss()
        }

        binding.delete.click {
            AlertDialog.Builder(it.context)
                .setTitle(getString(R.string.are_u_sure))
                .setPositiveButton(getString(R.string.sure)) { _, _ ->
                    vm.deleteCard(args.item.cardId)
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
                .show()
        }

        vm.deleteString.observeSingle(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}