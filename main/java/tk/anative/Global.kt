package tk.anative

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import tk.anative.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
object Global {
    lateinit var context: Context
    lateinit var activity: MainActivity
    lateinit var binding: ActivityMainBinding
    lateinit var webView : WebView
    external fun serverJNI(): Void
    var photoURI: Uri? = null
    lateinit var fileName: String
    lateinit var filePath: String

    // Returned Results holders
    lateinit var CameraResult: ActivityResultLauncher<Intent>
}