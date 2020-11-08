package com.claire.carddiary.utils

import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.ExifInterface.*
import android.net.Uri
import java.io.*


fun Uri.compressBySize(): ByteArray {

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(this.path, options)

    options.inSampleSize = calculateInSampleSize(options)
    options.inJustDecodeBounds = false

    val bitmap = BitmapFactory.decodeFile(this.path, options)
//    val rotatedBitmap = rotateBitmap(bitmap, this)

    val bos = ByteArrayOutputStream()
    bitmap?.compress(CompressFormat.JPEG, 80, bos)

    return bos.toByteArray()
}

private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    // 先根據寬度進行縮小
    while (width / inSampleSize > 1024) {
        inSampleSize++
    }
    // 再根據高度進行縮小
    while (height / inSampleSize > 1024) {
        inSampleSize++
    }
    return inSampleSize
}

private fun rotateBitmap(bitmap: Bitmap?, inputStream: InputStream): Bitmap? {
    if (bitmap == null) return null

    return try {
        val ei = ExifInterface(inputStream)
        val orientation: Int = ei.getAttributeInt(
            TAG_ORIENTATION,
            ORIENTATION_UNDEFINED
        )

        when (orientation) {
            ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
            ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
            ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
            ORIENTATION_NORMAL -> bitmap
            else -> bitmap
        }
    } catch (e: IOException) {
        e.printStackTrace()
        bitmap
    }
}

private fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}
