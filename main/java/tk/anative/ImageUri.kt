package tk.anative

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*

interface ImageUri {
    companion object {
        fun create() : Uri? {
            // Create an image file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            Global.fileName = "IMG_${timeStamp}.jpg"
            Global.filePath = "Pictures/tk.cocoon/IMG_${timeStamp}.jpg"

            val resolver = Global.context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, Global.fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                //put(MediaStore.MediaColumns.RELATIVE_PATH, "Android/media/tk.cocoon/")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/tk.cocoon/")
            }
            Global.photoURI = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            return Global.photoURI
        }
    }
}