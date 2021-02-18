package com.claire.carddiary.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.claire.carddiary.R
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.databinding.FragLoginBinding
import com.claire.carddiary.utils.click
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private val binding: FragLoginBinding by FragmentBindingProvider(R.layout.frag_login)
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                Snackbar.make(binding.root,
                    getString(R.string.login_success, AuthManager.user.value?.displayName),
                    Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, getString(R.string.login_fail), Snackbar.LENGTH_SHORT).show()
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

        binding.signInButton.click {
            startForResult.launch(AuthManager.login())
        }

        AuthManager.user.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(LoginFragmentDirections.toCardFragment())
            }
        }
    }

}