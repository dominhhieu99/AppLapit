package com.example.dung.applabit.loginandregister.login

import android.text.TextUtils
import com.example.dung.applabit.conmon.Constant
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginModel(private val onLoginListener: OnLoginListener) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkLogin(email: String, password: String) {
        val emailType = email.matches(Constant.EMAIL_TYPE.toRegex())

        if (TextUtils.isEmpty(email)) {
            onLoginListener.emailIsEmpty()
            return

        }
        if (TextUtils.isEmpty(password)) {
            onLoginListener.passwordIsEmpty()
            return

        }
        if (!emailType) {//kiem tra email hop le
            onLoginListener.emailInvalid()
            return

        }
        login(email, password)
    }

    fun login(email: String, password: String) {
        auth.signOut()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                onLoginListener.loginIsSuccess()
            } else {
                onLoginListener.loginIsFailed()
            }
        }
    }
}