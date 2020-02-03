package com.example.litechatter.helpers

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.litechatter.R


@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("layout_constraintHorizontal_bias")
fun setLayoutConstraintHorizontalBias(view: TextView, percent: Float) {
    val params = view.layoutParams as ConstraintLayout.LayoutParams
    params.horizontalBias = percent
    view.layoutParams = params
}