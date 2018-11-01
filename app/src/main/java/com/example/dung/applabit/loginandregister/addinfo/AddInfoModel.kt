package com.example.dung.applabit.loginandregister.addinfo

import android.text.TextUtils
import android.util.Log
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.MyApp

class AddInfoModel(private val onAddInfoListener: OnAddInfoListener) {

    fun checkAddInfo(name: String, gioiTinh: Boolean, ngaySinh: String, uri: String?) {

        Log.d("a", uri.toString() + "....")
        if (TextUtils.isEmpty(name)) {
            onAddInfoListener.onNameIsEmpty()
            return
        } else if (uri == "null") {
            Log.d("a", "....")
            onAddInfoListener.onUriIsempty()
            return
        } else if (!TextUtils.isEmpty(name)) {
            val user: User = User(null, name, ngaySinh.toLong(), uri, null, gioiTinh)
            MyApp.getInsatnce().user = user
            onAddInfoListener.onInfoSuccess()
            return
        }
        onAddInfoListener.onInfoFailer()
    }


    fun checkPermisstion(permisstion: Boolean) {
        if (permisstion) {
            //todo nho hon 6 hoac da duoc cap
            onAddInfoListener.onIsPermisstionGranted()
        } else {
            //todo lon hon 6 va chua duoc cap
            onAddInfoListener.onIsPermisstionNotGranted()
        }
    }
}