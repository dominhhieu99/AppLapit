package com.example.dung.applabit.main.profile

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.dung.applabit.R
import com.example.dung.applabit.base.MyBaseActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : MyBaseActivity(), OnProfileViewListener, View.OnClickListener {
    private var test: Int = 100
    private var drb: Drawable? = null

    private var mCurrentAnimator: Animator? = null

    private var mShortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

    }

    private fun init() {

        ProfilePresenter(this, this)
        imgAvatarP.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.imgAvatarP -> {
                if (drb != null) {
                    toast("000")
                    zoomImageFromThumb(imgAvatarP, drb!!)
                    mShortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                }
            }
        }
    }

    override fun onLoadImageSuccess(drawable: Drawable?) {
        drb = drawable
        imgAvatarP.setImageDrawable(drawable)
    }

    override fun onLoadImageFailed() {
        toast("tai anh that bai")
    }

    override fun onLoadDataSuccess(name: String, namSinh: String, gioiTinh: String) {
        txtName.text = name
        txtGioiTinh.text = gioiTinh
        txtNamSinh.text = namSinh
    }

    override fun onLoadDataFailed() {

    }

    override fun showProgressBar() {
        progressPA.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressPA.visibility = View.GONE
    }

    /**
     *  test
     *
     */

    private fun zoomImageFromThumb(thumbView: View, imageResId: Drawable) {

        mCurrentAnimator?.cancel()
        expanded_image.setImageDrawable(imageResId)

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()


        thumbView.getGlobalVisibleRect(startBoundsInt)
        findViewById<View>(R.id.container)
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)


        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {

            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {

            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        thumbView.alpha = 0f
        expanded_image.visibility = View.VISIBLE
        toast("ok ok ")

        expanded_image.pivotX = 0f
        expanded_image.pivotY = 0f

        mCurrentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expanded_image,
                    View.X,
                    startBounds.left,
                    finalBounds.left
                )
            ).apply {
                with(ObjectAnimator.ofFloat(expanded_image, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale, 1f))
            }
            duration = mShortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    mCurrentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    mCurrentAnimator = null
                }
            })
            start()

        }


///////////////////////////////////

        expanded_image.setOnClickListener {
            mCurrentAnimator?.cancel()

            mCurrentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expanded_image, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expanded_image, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expanded_image, View.SCALE_Y, startScale))
                }
                duration = mShortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expanded_image.visibility = View.GONE
                        mCurrentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expanded_image.visibility = View.GONE
                        mCurrentAnimator = null
                    }
                })
                start()
            }
        }
    }

}
