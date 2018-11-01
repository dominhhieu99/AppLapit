package com.example.dung.applabit.loginandregister.addinfo

interface OnAddInfoListener {

    fun onNameIsEmpty()
    fun onInfoSuccess()
    fun onInfoFailer()
    fun onIsPermisstionGranted()
    fun onIsPermisstionNotGranted()

    fun onUriIsempty()

}