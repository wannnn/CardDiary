package com.claire.carddiary.login

import androidx.lifecycle.LiveData
import com.claire.carddiary.data.model.User
import com.google.firebase.auth.FirebaseAuth

class FirebaseUserLiveData : LiveData<User?>() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    //  current FirebaseUser. You can utilize the FirebaseAuth.AuthStateListener callback to get
    //  updates on the current Firebase user logged into the app.
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
//        value = firebaseAuth.currentUser
        value = firebaseAuth.currentUser?.run {
            User(
                uid = uid,
                displayName = displayName.orEmpty(),
                picture = photoUrl.toString(),
                email = email.orEmpty()
            )
        }
    }

    // When this object has an active observer, start observing the FirebaseAuth state to see if
    // there is currently a logged in user.
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    // When this object no longer has an active observer, stop observing the FirebaseAuth state to
    // prevent memory leaks.
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}