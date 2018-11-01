package com.example.dung.applabit.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.util.MyUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileModel(private val onProfileListener: OnProfileListener, private val context: Context) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference
    private var storage: FirebaseStorage
    private var srf: StorageReference

    companion object {
        const val TAG = "ProfileModel"
    }

    init {
        reference = firebaseDatabase.reference
        storage = FirebaseStorage.getInstance()
        srf = storage.reference
        getData()

    }

    fun getData() {
        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                onProfileListener.onLoadDataFailed()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user: User = p0.getValue(User::class.java)!!

                Log.d("a", user.imageAvatarURL + "......")

                var gioiTinh: String = ""
                val ngaySinh = MyUtils().convertTime(user.ngaySinh, MyUtils.TYPE_DATE_D_M_YYYY)
                gioiTinh = if (user.gioiTinh) {
                    "nam"
                } else {
                    "nu"
                }

                onProfileListener.onLoadDataSuccess(user.name!!, ngaySinh, gioiTinh)
                loadImage(user.imageAvatarURL)
            }
        }
        reference.child("Users").child(auth.currentUser!!.uid).addValueEventListener(valueEventListener)
    }

    @SuppressLint("CheckResult")
    private fun loadImage(url: String?) {
        Glide.with(context)
                .load(Uri.parse(url)).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        onProfileListener.onLoadImageFailed()
                        Log.d(TAG, "ok...")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        return false
                    }
                }).into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        onProfileListener.onLoadImageSuccess(resource)
                    }
                })
    }



}