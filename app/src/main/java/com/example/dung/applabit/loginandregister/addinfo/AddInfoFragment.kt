package com.example.dung.applabit.loginandregister.addinfo


import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dung.applabit.MyApp
import com.example.dung.applabit.R
import com.example.dung.applabit.base.BaseFragment
import com.example.dung.applabit.conmon.Constant
import com.example.dung.applabit.loginandregister.LoginAndRegisterActivity
import com.example.dung.applabit.loginandregister.register.RegisterFragment
import com.example.dung.applabit.util.MyUtils
import kotlinx.android.synthetic.main.fragment_add_info.*
import java.util.*

class AddInfoFragment : BaseFragment(), View.OnClickListener, OnInfoViewListener {


    private lateinit var addInfoPresenter: AddInfoPresenter
    private var ngaySinh: Long = 0
    private var uri: Uri? = null

    companion object {
        val newFragment = AddInfoFragment()
        val TAG = "AddInfoFragment"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_info, container, false)
    }

    override fun init() {
        (activity as LoginAndRegisterActivity).setSupportActionBar(toobarAddInfo as Toolbar?)
        (activity as LoginAndRegisterActivity).supportActionBar!!.title = "Add Info"
        (toobarAddInfo as Toolbar).navigationIcon =
                resources.getDrawable(R.drawable.ic_arrow_back_black_24dp, (activity as LoginAndRegisterActivity).theme)
        (toobarAddInfo as Toolbar).setNavigationOnClickListener {

            /**
             *  neu back laithi gan tat ca bang null het (imgAvatarUri)
             *
             */
            MyApp.getInsatnce().imgAvatarUrl = null
            uri = null
            (activity as LoginAndRegisterActivity).popbacktask()
//            (activity as LoginAndRegisterActivity).addOrShowFragment(LoginFragment.newFragment, R.id.framelayout, true)
        }

        addInfoPresenter = AddInfoPresenter(this)
        edtNgaySinhA.text = MyUtils().convertTime(MyUtils().timeHere(), MyUtils.TYPE_DATE_D_M_YYYY)
        ngaySinh = MyUtils().timeHere()


        btnNext.setOnClickListener(this)
        edtNgaySinhA.setOnClickListener(this)
        imgAvatarA.setOnClickListener(this)

    }

    /**
     *  neu gioi tinh la true thi la nam
     */

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnNext -> {
                val name = edtNameA.text.toString()

                val gioiTinh = rbnNam.isChecked
                Toast.makeText(activity, uri.toString() + "...", Toast.LENGTH_SHORT).show()
                addInfoPresenter.checkInfo(name, gioiTinh, ngaySinh.toString(), uri.toString())
            }

            R.id.edtNgaySinhA -> {
                /**
                 *  bat dialog roi lay thoi gian ngay sinh ,khi vao dialog phai set ngay thang năm ccho no roi
                 *  moi get thoi gian khong se lay ngay mac dinh
                 *
                 */

                val calendar = Calendar.getInstance()
                val datePickerDialog =
                        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            calendar.set(year, month, dayOfMonth)
                            val time = calendar.timeInMillis
                            ngaySinh = time
                            edtNgaySinhA.text = MyUtils().convertTime(time, MyUtils.TYPE_DATE_D_M_YYYY)
                            toast(MyUtils().convertTime(time, MyUtils.TYPE_DATE_D_M_YYYY))

                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }

            R.id.imgAvatarA -> {

                val isPermisstion =
                        (activity as LoginAndRegisterActivity).hasPermission(Constant.WRITE_EXTERNAL_STORAGE)
                addInfoPresenter.checkPermisstion(isPermisstion)
            }
        }
    }


    /**
     *  da  dc cap se  goi camera
     */
    override fun onIsPermisstionGranted() {
        openImage()
    }

    /**
     *  neu khong dc cap thi khong bat thu vien anh
     *  ma khong co anh thi khong dc kyb dc
     *
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onIsPermisstionNotGranted() {
        //chua dc cap dc cap
        requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constant.REQESS_IMAGE
        )
    }


    /**
     *  @ERROR dang loi o day deo biet tai sao
     */

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constant.REQESS_IMAGE) {
            toast("vao day...")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //da dc cap
                toast("ok")
                openImage()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQESS_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast("data== null")
                return
            } else {
                uri = data.data
                Log.d("aa", "$uri......")
                val inputStream = activity!!.contentResolver.openInputStream(data.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imgAvatarA.setImageBitmap(bitmap)


                /**
                 *  hinh nhu tao ra 1  file
                 */
//                val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
//                Log.d(TAG, "${bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)}")
//                outputStream.close()
            }
        }
    }

    override fun onInfoSuccess() {
        MyApp.getInsatnce().imgAvatarUrl = uri.toString()
        (activity as LoginAndRegisterActivity).addOrShowFragment(RegisterFragment.newFragment, R.id.framelayout, true)
    }

    override fun onInfoFailer() {
        toast("loi!")
    }

    override fun onNameIsEmpty() {
        edtNameA.error = "Name is empty"
    }

    override fun onUriIsempty() {
        toast(" khong co ảnh !")
        Toast.makeText(activity, " khong co ảnh !", Toast.LENGTH_SHORT).show()
    }
}
