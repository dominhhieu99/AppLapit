package com.example.dung.applabit.loginandregister.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dung.applabit.R
import com.example.dung.applabit.base.BaseFragment
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.loginandregister.LoginAndRegisterActivity
import com.example.dung.applabit.loginandregister.addinfo.AddInfoFragment
import com.example.dung.applabit.main.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment(), View.OnClickListener, OnLoginViewListener {


    private lateinit var loginPrecenter: LoginPrecenter

    companion object {
        val newFragment = LoginFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun init() {

        loginPrecenter = LoginPrecenter(this)
        //
        loginPrecenter.checkLocationPermission(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION))
        btnLoginL.setOnClickListener(this)
        btnRegisterL.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegisterL -> {
                (activity as LoginAndRegisterActivity).addOrShowFragment(
                        AddInfoFragment.newFragment,
                        R.id.framelayout,
                        true
                )
            }
            R.id.btnLoginL -> {
                val email = edtEmailL.text.toString()
                val password = edtPasswordL.text.toString()
                loginPrecenter.checkLogin(email, password)

            }
        }
    }

    override fun checkLocationSuccess() {

    }

    /**
     *  permission location
     */

    override fun checkLocatiomFailed() {
        requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                Constant.REQESS_IMAGE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        toast("ok...")
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            toast("ok...")
        } else {
            (activity as LoginAndRegisterActivity).finish()
        }

    }

    override fun emailIsEmpty() {
        edtEmailL.error = "email is empty !"
    }

    override fun passwordIsEmpty() {
        edtPasswordL.error = "password is empty !"
    }

    override fun loginIsSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun emailInvalid() {
        edtEmailL.error = "email khong dung dinh dang !"
    }

    @SuppressLint("SetTextI18n")
    override fun loginIsFailed() {
        txtErrorL.text = "login is failed !"
    }

    override fun showProgressBar() {
        (activity as LoginAndRegisterActivity).showProgressDialog("", "")
    }

    override fun hideProgressBar() {
        (activity as LoginAndRegisterActivity).hideProgressDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPrecenter.onDestroy()
    }


}
