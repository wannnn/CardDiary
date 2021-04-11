package com.claire.carddiary.card

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.claire.carddiary.CardApplication
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream


object CompressUtils {

    fun compress(uri: Uri): ByteArray {

        var inputStream: InputStream? = null
        val baos = ByteArrayOutputStream()

        try {
            inputStream = CardApplication.instance.contentResolver.openInputStream(uri)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val bmp = BitmapFactory.decodeStream(inputStream)
        bmp.compress(Bitmap.CompressFormat.JPEG, 85, baos)
        baos.close()

        return baos.toByteArray()
    }

}