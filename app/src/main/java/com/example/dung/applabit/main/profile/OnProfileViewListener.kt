package com.example.dung.applabit.main.profile

import android.graphics.drawable.Drawable

interface OnProfileViewListener {

    fun showProgressBar()
    fun hideProgressBar()
    fun onLoadImageSuccess(drawable: Drawable?)
    fun onLoadImageFailed()

    fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String)
    fun onLoadDataFailed()

}