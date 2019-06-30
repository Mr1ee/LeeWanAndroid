package com.lee.leewanandroid.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lee.leewanandroid.R
import com.lee.leewanandroid.widget.ToastUtils
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {
    private var fromXml = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_animation)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        btn_translate.setOnClickListener {
            val animation = if (fromXml) {
                AnimationUtils.loadAnimation(this, R.anim.translate_anim)
            } else {
                val animationCode = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
                )
                animationCode.duration = 3000L
                animationCode.fillAfter = true
                animationCode.interpolator = AccelerateInterpolator()
                animationCode
            }

            iv_animation_like.startAnimation(animation)
        }
        btn_rotate.setOnClickListener {
            val animation = if (fromXml) {
                AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
            } else {
                val animationCode = RotateAnimation(0f, 180f)
                animationCode.duration = 3000L
                animationCode.fillAfter = true
                animationCode.interpolator = AccelerateInterpolator()
                animationCode
            }

            iv_animation_like.startAnimation(animation)
        }
        btn_scale.setOnClickListener {
            val animation = if (fromXml) {
                AnimationUtils.loadAnimation(this, R.anim.scale_anim)
            } else {
                val animationCode = ScaleAnimation(1f, 2f, 1f, 2f)
                animationCode.duration = 3000L
                animationCode.fillAfter = true
                animationCode.interpolator = AccelerateInterpolator()
                animationCode
            }

            iv_animation_like.startAnimation(animation)
        }
        btn_alpha.setOnClickListener {
            val animation = if (fromXml) {
                AnimationUtils.loadAnimation(this, R.anim.alpha_anim)
            } else {
                val animationCode = AlphaAnimation(1f, 0.1f)
                animationCode.duration = 3000L
                animationCode.fillAfter = true
                animationCode.interpolator = AccelerateInterpolator()
                animationCode
            }

            iv_animation_like.startAnimation(animation)
        }

        btn_load_from_xml.setOnClickListener {
            fromXml = !fromXml
            btn_load_from_xml.text = "现在演示的动画是" + if (fromXml) {
                "XML加载"
            } else {
                "代码生成"
            }
        }

        iv_animation_like.setOnClickListener {
            ToastUtils.showToast("点击了心型View！！！")
        }

        cl_parent_container.setOnClickListener {
            ToastUtils.showToast("点击了父布局！！！")
        }
    }
}