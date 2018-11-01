package com.example.dung.applabit.loginandregister.register

import com.example.dung.applabit.base.BasePresenter

class RegisterPresenter(private var onRegisterViewListener: OnRegisterViewListener?) : BasePresenter(),
    OnRegisterListener {


    private val registerModel = RegisterModel(this)

    fun insertData(email: String, password: String, rePassword: String, imgAvatarUri: String) {
        if (onRegisterViewListener != null) {
            onRegisterViewListener!!.showProgressBar()
        }

        registerModel.inserData(email, password, rePassword, imgAvatarUri)

    }


    override fun registerIsSuccess() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.registerIsSuccess()
    }

    override fun registerIsFailed() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.registerIsFailed()
    }

    override fun addDataFailed() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.addDataFailed()
    }

    override fun passwordIsEmpty() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.passwordIsEmpty()
    }

    override fun passwordShort() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.passwordShort()
    }

    override fun emailIsEmpty() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.emailIsEmpty()
    }

    override fun emailInvalid() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.emailInvalid()
    }

    override fun identicalPassword() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.identicalPassword()
    }

    override fun upImageFailed() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.upImageFailed()
    }

    override fun downLinkFailed() {
        onRegisterViewListener!!.hideProgressBar()
        onRegisterViewListener!!.downLinkFailed()
    }

    override fun onDestroy() {
        if (onRegisterViewListener != null) {
            onRegisterViewListener = null
        }
    }


}