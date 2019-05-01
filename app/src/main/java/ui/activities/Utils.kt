package ui.activities

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.anonymous.cikgood.R

/**
 * Created by Al
 * on 06/04/19 | 22:22
 */
class Utils {

    fun imageRound(imageView: ImageView, url: String?, context: Context, scaleType: ImageView.ScaleType? = null) {
        url?.let {
            val radius = context.resources.getDimensionPixelSize(R.dimen.radius)
            var requestOptions = RequestOptions()

            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(radius))
            requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            if(scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestOptions = requestOptions.centerInside()
            }

            Glide.with(context)
                    .load(it)
                    .apply(requestOptions)
                    .into(imageView)
        }
    }

}