package yqtrack.ap.file

import android.content.Context
import yqtrack.ap.utils.const.Link

class FileManager(
    private val context: Context
) {
    fun takeFileData() = try {
        context.openFileInput(NAME).bufferedReader().use { it.readLine() }
    } catch (t: Throwable) {
        null
    }

    fun putFileData(link: String) {
        if (takeFileData() == null && Link.INITIAL !in link) {
            context.openFileOutput(NAME, Context.MODE_PRIVATE).use {
                it.write(link.toByteArray())
            }
        }
    }

    companion object {
        private const val NAME = "eksFile"
    }
}