package tk.anative

import android.content.Intent
import android.provider.MediaStore

interface FileChooser {
    companion object {
        fun create() {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            takePictureIntent.resolveActivity(Global.activity.packageManager)?.also {
                try {
                    Global.photoURI = ImageUri.create()
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Global.photoURI)
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    Util.displayMessage(ex.message.toString())
                }
            }

          //  Global.CameraResult.launch(takePictureIntent)

            val intentArray: Array<Intent?> = arrayOf(takePictureIntent)
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.let{
                it.addCategory(Intent.CATEGORY_OPENABLE)
                          it.type = "image/*"
            }

          val chooserIntent = Intent(Intent.ACTION_CHOOSER)

          chooserIntent.let {
              it.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
              it.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
              it.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            //   Global.activity.startActivity(it)
              Global.CameraResult.launch(it)
          }
        }
    }
}