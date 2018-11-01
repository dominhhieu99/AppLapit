package com.example.dung.applabit.loginandregister.register

import android.net.Uri
import android.text.TextUtils
import com.example.dung.applabit.Model.User
import com.example.dung.applabit.MyApp
import com.example.dung.applabit.conmon.Constant
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class RegisterModel(private val onRegisterListener: OnRegisterListener) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference: DatabaseReference

    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var storageRf: StorageReference

    init {
        reference = firebaseDatabase.reference
        storageRf = storage.reference

    }

    fun inserData(email: String, password: String, rePassword: String, imgAvatarUri: String) {

        val emailType = email.matches(Constant.EMAIL_TYPE.toRegex())

        if (!emailType) {
            onRegisterListener.emailInvalid()
            return
        }
        if (password.length < 6) {
            onRegisterListener.passwordShort()
            return
        }
        if (TextUtils.isEmpty(email)) {
            onRegisterListener.emailIsEmpty()
            return
        }
        if (TextUtils.isEmpty(password)) {
            onRegisterListener.passwordIsEmpty()
            return
        }
        if (password != rePassword) {
            //todo mat khau khong khop
            onRegisterListener.identicalPassword()
            return
        }

        val user: User = MyApp.getInsatnce().user
        val name = user.name
        val ngaySinh = user.ngaySinh
        val gioiTinh = user.gioiTinh
        registerUser(email, password, name, ngaySinh, gioiTinh, imgAvatarUri)

    }

    fun registerUser(
            email: String,
            password: String,
            name: String?,
            ngaySinh: Long,
            gioiTinh: Boolean,
            imgAvatarUri: String
    ) {
        auth.signOut()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                //them anh vao
                storageRf = storageRf.child("Avatars").child(auth.currentUser!!.uid)
                val lineLinePhoto = storageRf.putFile(Uri.parse(imgAvatarUri))          //doi ve uri
                lineLinePhoto.addOnCompleteListener { task: Task<UploadTask.TaskSnapshot> ->
                    if (task.isSuccessful) {
                        //todo up image thanh cong
                        storageRf.downloadUrl.addOnSuccessListener { uri ->
                            if (task.isSuccessful) {
                                //todo tai link thanh cong
                                updata(name, ngaySinh, gioiTinh, uri.toString())
                            } else {
                                //todo tai link that bai
                                onRegisterListener.downLinkFailed()
                            }
                        }

                    } else {
                        //todo up image that bai
                        onRegisterListener.upImageFailed()
                    }
                }
            } else {
                //todo dang ky khong thanh cong
                onRegisterListener.registerIsFailed()
            }
        }
    }

    fun updata(
            name: String?,
            ngaySinh: Long,
            gioiTinh: Boolean,
            imgAvatarUri: String
    ) {
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["idUser"] = auth.currentUser!!.uid
        hashMap["name"] = name
        hashMap["ngaySinh"] = ngaySinh
        hashMap["imageAvatarURL"] = imgAvatarUri
        hashMap["gioiTinh"] = gioiTinh

        reference.child("Users").child(auth.currentUser!!.uid).setValue(hashMap)
                .addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        //todo add data thanh cong
                        onRegisterListener.registerIsSuccess()
                    } else {
                        //todo add data khong thanh cong
                        onRegisterListener.addDataFailed()
                    }
                }
    }
}