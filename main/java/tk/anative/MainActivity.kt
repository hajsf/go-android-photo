package tk.anative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tk.anative.Global.binding
import tk.anative.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Global.context = baseContext
        Global.activity = this
        CameraResults.register()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Global.webView = binding.webView
        WebListener.initiate()
        WebSettings.setUp()

        binding.sampleText.text = stringFromJNI()
    }

    private external fun stringFromJNI(): String

    companion object {
        // Used to load the 'anative' library on application startup.
        init {
            System.loadLibrary("anative")
        }
    }
}