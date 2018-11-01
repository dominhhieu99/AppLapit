package com.example.dung.applabit.main.profile

import android.graphics.drawable.Drawable

interface OnProfileListener {

    fun onLoadImageSuccess(drawable: Drawable?)
    fun onLoadImageFailed()

    fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String)
    fun onLoadDataFailed()

}