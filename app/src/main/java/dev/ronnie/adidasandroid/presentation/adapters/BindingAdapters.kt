package dev.ronnie.adidasandroid.presentation.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Loads the image to calling [ImageView] using [Glide] using the [androidx.databinding] BindingAdapter
 * Checks if the image passed needed to be rounded
 */
@BindingAdapter("imageFromUrl", "shouldRound")
fun bindImageFromUrl(view: ImageView, imageUrl: String?, shouldRound: Boolean) {
    if (!imageUrl.isNullOrEmpty()) {
        val glide = Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
        if (shouldRound) glide.transform(CenterCrop(), RoundedCorners(10))
        glide.into(view)
    }
}