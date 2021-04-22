package com.claire.carddiary.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.claire.carddiary.R
import com.claire.carddiary.base.FragmentBindingProvider
import com.claire.carddiary.databinding.FragProfileBinding
import com.claire.carddiary.login.AuthManager
import com.claire.carddiary.utils.click
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private val binding: FragProfileBinding by FragmentBindingProvider(R.layout.frag_profile)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = AuthManager.user.value

        binding.signOut.click {
            AuthManager.logOut {
                if (it) {
                    Snackbar.make(binding.root, getString(R.string.logout_success), Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, getString(R.string.logout_fail), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}