package com.example.dung.applabit.loginandregister.addinfo

interface OnInfoViewListener {

    fun onInfoSuccess()
    fun onInfoFailer()
    fun onNameIsEmpty()

    fun onIsPermisstionGranted()
    fun onIsPermisstionNotGranted()

    fun onUriIsempty()
}