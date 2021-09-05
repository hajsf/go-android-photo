package tk.anative

import android.widget.Toast

interface Util {
    companion object {
        fun displayMessage(message: String) {
            Toast.makeText(Global.context, message, Toast.LENGTH_LONG).show()
        }
    }
}