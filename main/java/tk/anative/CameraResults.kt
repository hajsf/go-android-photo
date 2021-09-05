package tk.anative

import android.app.Activity.RESULT_OK
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileNotFoundException

interface CameraResults {
    companion object {
        fun register() {
            Global.CameraResult = Global.activity.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                    if (result.resultCode == RESULT_OK) {
                        result.data?.let {
                            val f = GetFileFromUrl.getFileFromUri(it.data!!)
                            Global.activity.contentResolver.query(it.data!!, null, null, null, null)
                            ?.use { cursor ->
                                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                                cursor.moveToFirst()
                              //  val n = cursor.getString(nameIndex)
                              //  val s = cursor.getLong(sizeIndex).toString()
                              //  Util.displayMessage("$n / $s")
                            }

                            when (it.extras) {}
                        }
                        Global.webView.loadUrl("javascript:showResponse()")
                    } else {
                       // Util.displayMessage("Sorry no image camputred")
                    }
                }
      }
    }
}
