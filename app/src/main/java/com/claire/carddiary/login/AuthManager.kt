package com.claire.carddiary.login

import android.content.Intent
import com.claire.carddiary.CardApplication
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder

object AuthManager {

    private val providers by lazy { listOf(GoogleBuilder().build()) }

    val user = FirebaseUserLiveData()

    val userId = user.value?.uid.orEmpty()

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

}