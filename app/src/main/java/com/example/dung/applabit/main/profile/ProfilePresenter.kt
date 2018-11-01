package com.example.dung.applabit.main.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

class ProfilePresenter(
        private val onProfileViewListener: OnProfileViewListener,
        private val context: Context
) :
        OnProfileListener {

    private val profileModel: ProfileModel = ProfileModel(this, context)

    init {

        onProfileViewListener.showProgressBar()
    }

    fun zoomImageFromThumb(imgAvatarP: Int, drb: Drawable) {
//        profileModel.zoomImageFromThumb(imgAvatarP, drb)

    }

    override fun onLoadImageSuccess(drawable: Drawable?) {
        onProfileViewListener.hideProgressBar()
        onProfileViewListener.onLoadImageSuccess(drawable)
    }

    override fun onLoadImageFailed() {
        onProfileViewListener.hideProgressBar()
        onProfileViewListener.onLoadImageFailed()
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String) {
        onProfileViewListener.onLoadDataSuccess(name, namSinh, gioiTinh)
    }

    override fun onLoadDataFailed() {
        onProfileViewListener.onLoadDataFailed()

    }


}