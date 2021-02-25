package com.example.movieapp.Util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    if (url != "") {
        Glide.with(context).load(url).into(this)
    }
}