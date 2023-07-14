package joalissongomes.dev.instagram.common.util

import android.app.Activity
import android.util.Log
import joalissongomes.dev.instagram.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Files {

    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    fun generateFile(activity: Activity): File {
        val mediaDir = activity.externalMediaDirs.firstOrNull()?.let {
            File(it, activity.getString(R.string.app_name)).apply {
                mkdirs()
            }
        }

        val outputDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else activity.filesDir
        
        val fileName =
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"

        return File(outputDir, fileName)
    }
}
