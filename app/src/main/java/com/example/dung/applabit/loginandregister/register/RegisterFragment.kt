package com.example.dung.applabit.loginandregister.register


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dung.applabit.MyApp
import com.example.dung.applabit.R
import com.example.dung.applabit.base.BaseFragment
import com.example.dung.applabit.loginandregister.LoginAndRegisterActivity
import com.example.dung.applabit.main.MainActivity
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment(), View.OnClickListener, OnRegisterViewListener {


    private lateinit var registerPresenter: RegisterPresenter

    companion object {
        val newFragment = RegisterFragment()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun init() {

        (activity as LoginAndRegisterActivity).setSupportActionBar(toobarRegister as Toolbar?)
        (activity as LoginAndRegisterActivity).supportActionBar!!.title = "Add Info"
        (toobarRegister as Toolbar).navigationIcon =
                resources.getDrawable(R.drawable.ic_arrow_back_black_24dp, (activity as LoginAndRegisterActivity).theme)

        (toobarRegister as Toolbar).setNavigationOnClickListener {
            //            (activity as LoginAndRegisterActivity).addOrShowFragment(
//                AddInfoFragment.newFragment,
//                R.id.framelayout,
//                false
//            )

            (activity as LoginAndRegisterActivity).popbacktask()
        }

        registerPresenter = RegisterPresenter(this)
        btnRegisterR.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegisterR -> {
                val email = edtEmailR.text.toString()
                val password = edtPasswordR.text.toString()
                val rePassword = edtRePasswordR.text.toString()
                val imgAvatarUri = MyApp.getInsatnce().imgAvatarUrl
                registerPresenter.insertData(email, password, rePassword, imgAvatarUri)

            }
        }
    }


    override fun registerIsSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    override fun registerIsFailed() {
        txtErrorR.text = "Đăng ký that bai"
    }

    @SuppressLint("SetTextI18n")
    override fun addDataFailed() {
        txtErrorR.text = "them thong tin that bai"
    }

    override fun passwordIsEmpty() {
        edtPasswordR.error = "password is empty !"
    }

    override fun passwordShort() {
        edtPasswordR.error = "mat khau ngan !"
    }

    override fun emailIsEmpty() {
        edtEmailR.error = "email is empty !"
    }

    override fun emailInvalid() {
        edtEmailR.error = "email khong hop le !"
    }

    override fun identicalPassword() {
        edtRePasswordR.error = "mat khau khong khop !"
    }

    @SuppressLint("SetTextI18n")
    override fun upImageFailed() {
        txtErrorR.text = "up image failed"
    }

    @SuppressLint("SetTextI18n")
    override fun downLinkFailed() {
        txtErrorR.text = "down link image failed"
    }

    override fun showProgressBar() {
        (activity as LoginAndRegisterActivity).showProgressDialog("", "")
    }

    override fun hideProgressBar() {
        (activity as LoginAndRegisterActivity).hideProgressDialog()
    }

}
