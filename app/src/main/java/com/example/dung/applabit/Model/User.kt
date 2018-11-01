package com.example.dung.applabit.Model

class User {
    var idUser: String? = null
    var name: String? = null
    var ngaySinh: Long = -1
    var imageAvatarURL: String? = null
    var discribe: String? = null       //mo ta...
    var gioiTinh: Boolean = false

    constructor()

    constructor(idUser: String?, name: String?, ngaySinh: Long, imageAvatarURL: String?, discribe: String?, gioiTinh: Boolean) {
        this.idUser = idUser
        this.name = name
        this.ngaySinh = ngaySinh
        this.imageAvatarURL = imageAvatarURL
        this.discribe = discribe
        this.gioiTinh = gioiTinh
    }

}