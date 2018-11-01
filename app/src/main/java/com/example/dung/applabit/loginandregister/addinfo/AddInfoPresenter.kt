package com.example.dung.applabit.loginandregister.addinfo

class AddInfoPresenter(private var onInfoViewListener: OnInfoViewListener?) : OnAddInfoListener {


    private val addInfoModel = AddInfoModel(this)
    fun checkInfo(name: String, gioiTinh: Boolean, ngaySinh: String, uri:String) {
        addInfoModel.checkAddInfo(name, gioiTinh, ngaySinh, uri)
    }

    fun checkPermisstion(permisstion: Boolean) {
        addInfoModel.checkPermisstion(permisstion)
    }

    override fun onInfoSuccess() {
        onInfoViewListener!!.onInfoSuccess()
    }

    override fun onInfoFailer() {
        onInfoViewListener!!.onInfoFailer()
    }

    override fun onNameIsEmpty() {
        onInfoViewListener!!.onNameIsEmpty()
    }

    override fun onIsPermisstionGranted() {
        //todo
        onInfoViewListener!!.onIsPermisstionGranted()
    }

    override fun onIsPermisstionNotGranted() {
        //todo
        onInfoViewListener!!.onIsPermisstionNotGranted()
    }

    override fun onUriIsempty() {
        onInfoViewListener!!.onUriIsempty()
    }

    fun onDestroy() {
        if (onInfoViewListener != null) {
            onInfoViewListener = null
        }
    }
}