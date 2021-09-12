package ms.saghafi.baloot.utils

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ms.saghafi.baloot.R
import java.text.DecimalFormat


fun Fragment.showToastMessage(message: String?){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
fun Activity.showToastMessage(message: String?){
    Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
}

//change Status Toolbar Background to White
@Suppress("DEPRECATION")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarWhite() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.white)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

@BindingAdapter("avatar")
fun ImageView.avatar(img: Bitmap) {
    val round = RoundedBitmapDrawableFactory.create(resources, img)
    round.isCircular = true
    setImageDrawable(round)
}

fun View.setVisible(){
    this.visibility = View.VISIBLE
}

fun View.setInvisible(){
    this.visibility = View.INVISIBLE
}

fun View.setGone(){
    this.visibility = View.GONE
}

fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit){

    this.addOnScrollListener(object: RecyclerView.OnScrollListener(){

        private val visibleThreshold = 1
        private var loading = true
        private var previousTotal = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            with(layoutManager as LinearLayoutManager){

                val visibleItemCount = childCount
                val totalItemCount = itemCount
                val firstVisibleItem = findFirstVisibleItemPosition()

                if (loading && totalItemCount > previousTotal){
                    loading = false
                    previousTotal = totalItemCount
                }

                if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
                    onScrolledToEnd()
                    loading = true
                }
            }
        }
    })
}

