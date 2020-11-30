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
import androidx.fragment.app.viewModels
import com.claire.carddiary.databinding.FragLoginBinding
import com.claire.carddiary.utils.click
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private lateinit var binding: FragLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                    // updateUi
                    Snackbar.make(binding.root, "SignIn Success！", Snackbar.LENGTH_SHORT).show()
                } catch (e: ApiException) {
                    Snackbar.make(binding.root, "SignIn Fail！", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragLoginBinding.inflate(inflater, container, false)
        return binding.apply {
            vm = viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInIntent = GoogleSignIn.getClient(requireActivity(), gso).signInIntent

        binding.signInButton.click {
            startForResult.launch(signInIntent)
        }
    }

}