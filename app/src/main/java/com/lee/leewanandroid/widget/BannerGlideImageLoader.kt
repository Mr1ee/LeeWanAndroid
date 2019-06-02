package com.lee.leewanandroid.widget

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoaderInterface

class BannerGlideImageLoader : ImageLoaderInterface<ImageView> {
    override fun createImageView(context: Context?): ImageView {
        return ImageView(context)
    }

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        context?.let { it1 ->
            imageView?.let { it2 ->
                Glide.with(it1).load(path).into(it2)
            }
        }
    }
}