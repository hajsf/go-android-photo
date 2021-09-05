package tk.anative

import android.content.Context
import android.os.Build
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface
internal constructor(var mContext: Context) {
    @get:JavascriptInterface
    val androidVersion: Int
        get() = Build.VERSION.SDK_INT

    @get:JavascriptInterface
    val responseMessage: String
        get() = Global.filePath

    // Show a toast from the web page
    @JavascriptInterface
    fun showToast(toast: String?) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}