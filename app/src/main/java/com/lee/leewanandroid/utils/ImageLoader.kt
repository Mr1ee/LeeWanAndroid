package com.lee.leewanandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lee.leewanandroid.GlideApp
import com.lee.leewanandroid.R

object ImageLoader {

    /**
     *
     * @param context context
     * @param url image url
     * @param iv imageView
     */
    fun load(context: Context, url: String, iv: ImageView) {
        GlideApp.with(context)
            .load(url)
            .placeholder(R.drawable.bg_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(iv)

    }
}