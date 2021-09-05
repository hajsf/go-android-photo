package tk.anative

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.webkit.WebChromeClient
import android.webkit.ValueCallback
import android.webkit.WebView

interface WebListener {
    companion object {
        fun initiate() {
            Global.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                   // view.loadUrl("javascript:alert('Welcome to native app')")
                }
            }

          Global.webView.webChromeClient = object : WebChromeClient() {
              override fun onShowFileChooser(
                    webView: WebView,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams
                ): Boolean {
                    if (ContextCompat.checkSelfPermission(
                            Global.context,
                            Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            Global.activity,
                            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            0
                        )
                    } else {
                        FileChooser.create()
                    }
                    filePathCallback.onReceiveValue(null)
                    return true
                }
            }
        }
    }
}