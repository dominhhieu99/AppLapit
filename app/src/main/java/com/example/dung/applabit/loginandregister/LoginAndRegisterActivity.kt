package com.example.dung.applabit.loginandregister

import android.os.Bundle
import com.example.dung.applabit.R
import com.example.dung.applabit.base.MyBaseActivity
import com.example.dung.applabit.loginandregister.login.LoginFragment

class LoginAndRegisterActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_register)
        addOrShowFragment(LoginFragment.newFragment, R.id.framelayout, false)

        init()
    }

    private fun init() {

    }
}
