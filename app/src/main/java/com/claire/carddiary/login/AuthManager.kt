package com.claire.carddiary.login

import android.content.Intent
import com.claire.carddiary.CardApplication
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

object AuthManager {

    private val providers by lazy { listOf(AuthUI.IdpConfig.GoogleBuilder().build()) }

    fun login(): Intent =
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

    fun logOut(callback: (isSuccess: Boolean) -> Unit) {
        AuthUI.getInstance().signOut(CardApplication.instance)
            .addOnSuccessListener {
                callback.invoke(true)
            }
            .addOnFailureListener {
                callback.invoke(false)
            }
    }

    fun getUser(): String =
        FirebaseAuth.getInstance().currentUser?.displayName.orEmpty()

}