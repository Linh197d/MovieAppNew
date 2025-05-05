package com.qibla.muslimday.app2025.util

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Hashtable
import java.util.Locale

object UtilsClass {
    var DOWNLOADED_ENGLISH_FONTS = Hashtable<String?, Typeface?>()
    var DOWNLOADED_FONTS = Hashtable<String?, Typeface?>()
    fun range(f: Float, f2: Float, f3: Float): Float {
        return f3 * (f2 - f) / 100.0f
    }

    fun isAppInstalled(context: Context, str: String?): Boolean {
        return try {
            context.packageManager.getApplicationInfo(str!!, 0)
            true
        } catch (unused: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun dpToPx(context: Context, f: Float): Int {
        val round = Math.round(context.resources.displayMetrics.xdpi / 160.0f * f)
        return if (round < 0) f.toInt() else round
    }

    fun getPxofDp(context: Context, i: Int): Int {
        val d = (i.toFloat() * context.resources.displayMetrics.density).toDouble()
        java.lang.Double.isNaN(d)
        return (d + 0.5).toInt()
    }

    fun getImageBigThanRequiredHavingPath(str: String?, i: Int, i2: Int): Bitmap {
        Log.d("inside get small ", "inside get small")
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(str, options)
        options.inSampleSize = calculateInSampleSize1(options, i, i2)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(str, options)
    }

    private fun drawableToBitmap(width: Int, height: Int, drawable: Drawable?): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable == null) return null
        try {
            if (drawable is BitmapDrawable) {
                bitmap = drawable.bitmap
                if (bitmap != null && bitmap.height > 0) {
                    val matrix = Matrix()
                    val scaleWidth = width * 1.0f / bitmap.width
                    val scaleHeight = height * 1.0f / bitmap.height
                    matrix.postScale(scaleWidth, scaleHeight)
                    bitmap =
                        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                    return bitmap
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.apply {
                setBounds(0, 0, canvas.width, canvas.height)
                draw(canvas)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.let {
            val bitmap =
                Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
            if (bitmap != null) {
                val canvas = Canvas(bitmap)
                it.apply {
                    setBounds(0, 0, canvas.width, canvas.height)
                    draw(canvas)
                }
                return bitmap
            }
        }
        return null
    }

    fun calculateInSampleSize1(options: BitmapFactory.Options, i: Int, i2: Int): Int {
        val i3 = options.outHeight
        val i4 = options.outWidth
        var i5 = 1
        if (i3 > i2 || i4 > i) {
            val i6 = i3 / 2
            val i7 = i4 / 2
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2
            }
        }
        return i5
    }

    fun getImageSmallThanRequired(resources: Resources?, i: Int, i2: Int, i3: Int): Bitmap {
        Log.d("inside get small ", "inside get small")
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, i, options)
        options.inSampleSize = calculateInSampleSize1(options, i2, i3)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, i, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, i: Int, i2: Int): Int {
        val i3 = options.outHeight
        val i4 = options.outWidth
        Log.d("origanl bit size ", "$i4 -- $i3")
        var i5 = 1
        if (i3 > i2 || i4 > i) {
            val d = i3.toDouble()
            java.lang.Double.isNaN(d)
            val i6 = (d * 1.1).toInt()
            val d2 = i4.toDouble()
            java.lang.Double.isNaN(d2)
            val i7 = (d2 * 1.1).toInt()
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5++
            }
        }
        Log.d("sample size ", "samaple size")
        return i5
    }

    fun calculateInSampleSizen(options: BitmapFactory.Options, i: Int, i2: Int): Int {
        val i3 = options.outHeight
        val i4 = options.outWidth
        var i5 = 1
        if (i3 > i2 || i4 > i) {
            val i6 = i3 / 2
            val i7 = i4 / 2
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2
            }
        }
        return i5
    }

    fun getImageSmallThanRequiredHavingPath(str: String?, i: Int, i2: Int): Bitmap {
        Log.d("inside get small ", "inside get small")
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(str, options)
        Log.d("Original Bitmap", "HIt : " + options.outHeight + " Wid : " + options.outWidth)
        if (options.outWidth < i || options.outHeight < i2) {
            options.inSampleSize = 1
        } else {
            options.inSampleSize = calculateInSampleSize(options, i, i2)
        }
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(str, options)
    }

    fun getPath(uri: Uri, context: Context): String? {
        val scheme = uri.scheme
        Log.d("DEBUG", "scheme : $scheme")
        Log.d("DEBUG", "URI Path : " + uri.path)
        val pathSegments = uri.pathSegments
        val equalsIgnoreCase = scheme.equals("file", ignoreCase = true)
        var equalsIgnoreCase2 = scheme.equals("content", ignoreCase = true)
        for (next in pathSegments) {
            if (next.startsWith("file")) {
                return next.substring(7)
            }
            if (next.startsWith("content")) {
                equalsIgnoreCase2 = true
            }
            Log.d("DEBUG", "Path Segments : $next")
        }
        if (equalsIgnoreCase) {
            return uri.path
        }
        if (equalsIgnoreCase2) {
            val query = context.contentResolver.query(
                uri,
                arrayOf("_data"),
                null as String?,
                null as Array<String?>?,
                null as String?
            )
            Log.d("DEBUG", "Cursor : $query")
            if (!(query == null || query.count == 0)) {
                val columnIndexOrThrow = query.getColumnIndexOrThrow("_data")
                query.moveToFirst()
                return query.getString(columnIndexOrThrow)
            }
        }
        return null
    }

    fun dpToPixel(f: Float, context: Context): Float {
        return f * (context.resources.displayMetrics.densityDpi.toFloat() / 160.0f)
    }

    val currentDateWithTime: String
        get() {
            val charArray =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
                    .toCharArray()
            val cArr = CharArray(charArray.size - 5)
            var i = 0
            for (i2 in charArray.indices) {
                if (charArray[i2] in '0'..'9') {
                    cArr[i] = charArray[i2]
                    i++
                }
            }
            return (cArr.toString())
        }

    fun angleBetweenTwoPointsWithFixedPoint(
        d: Double,
        d2: Double,
        d3: Double,
        d4: Double,
        d5: Double,
        d6: Double,
    ): Double {
        return Math.atan2(d4 - d6, d3 - d5) - Math.atan2(d2 - d6, d - d5)
    }

    fun getPointOnCircle(f: Float, d: Double, pointF: PointF): PointF {
        val d2 = f.toDouble()
        val cos = Math.cos(Math.toRadians(d))
        java.lang.Double.isNaN(d2)
        val sin = Math.sin(Math.toRadians(d))
        java.lang.Double.isNaN(d2)
        return PointF((cos * d2).toFloat() + pointF.x, (d2 * sin).toFloat() + pointF.y)
    }

    //    fun deleteFileInMultipleDirectories(fileName: String) {
//
//        val directoryDocuments =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//        val fileDocuments = File(
//            directoryDocuments,
//            "${AppConstant.LOGO_FOLDER_NAME}/$fileName${AppConstant.SAVED_LOGO_TXT}"
//        )
//
//
//        if (fileDocuments.exists()) {
//            fileDocuments.delete()
//        }
//    }
    fun removeEndPNGFile(tenFile: String): String {
        return if (tenFile.endsWith(".png")) {
            tenFile.substring(0, tenFile.length - 4) // Xóa 4 ký tự cuối cùng (.png)
        } else {
            tenFile
        }
    }

    fun removeEndTXTFile(tenFile: String): String {
        return if (tenFile.endsWith(".txt")) {
            tenFile.substring(0, tenFile.length - 4) // Xóa 4 ký tự cuối cùng (.png)
        } else {
            tenFile
        }
    }

    fun replacePNGWithTXT(tenFile: String): String {
        return if (tenFile.endsWith(".png")) {
            tenFile.substring(0, tenFile.length - 4) + ".txt" // Thay thế .png bằng .txt
        } else {
            tenFile
        }
    }

    fun getStringBetweenLastSlashAndExtension(
        fileName: String,
        char1: Char,
        char2: String,
    ): String {
        val check = fileName.contains(char1)
        val lastSlashIndex = fileName.lastIndexOf(char1) // Tìm vị trí của kí tự '/' cuối cùng
//        val endIndex = if (check) fileName.lastIndexOf(AppConstant.SAVED_LOGO_EXTENSION) else fileName.lastIndexOf(AppConstant.SAVED_LOGO_EXTENSION_JPG)  // Tìm vị trí trước kí tự '.png'
        val endIndex = fileName.lastIndexOf(char2)  // Tìm vị trí trước kí tự '.png'

        // Kiểm tra nếu tìm thấy '/' và '.png' và '/' đứng trước '.png'
        if (lastSlashIndex in 0 until endIndex) {
            return fileName.substring(lastSlashIndex + 1, endIndex)
        }

        return "" // Trả về chuỗi rỗng nếu không tìm thấy hoặc vị trí không hợp lệ
    }

//    fun getAllFilesPathTxt(context: Context): Vector<String> {
//        val listData: Vector<String> = Vector()
//        val externalFilesDir = context.getExternalFilesDir(null)
//        val i = externalFilesDir
//        val file: File = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
//                .toString() + File.separator + AppConstant.LOGO_FOLDER_NAME
//        )
//
////        val file: File = File(
////            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
////                .toString() + File.separator + AppConstant.LOGO_FOLDER_NAME
////        )
//        if (file.isDirectory) {
//            val listFiles: Array<File> = file.listFiles()
//            val pairArr: Array<Pair?> = arrayOfNulls(listFiles.size)
//            for (i in listFiles.indices) {
//                pairArr[i] = Pair(listFiles[i])
//            }
//
//            Arrays.sort(pairArr)
//            for (length in pairArr.indices.reversed()) {
//                val file2: File = pairArr[length]!!.f88f
//                if (!file2.isDirectory && file2.name
//                        .endsWith(AppConstant.SAVED_LOGO_TXT)
//                ) {
//                    listData.add(file2.absolutePath)
//                }
//            }
//
//        }
//        return listData
//    }

    internal class Pair constructor(var f88f: File) : Comparable<Any?> {
        var f89t: Long = f88f.lastModified()

        public override fun compareTo(obj: Any?): Int {
            val j: Long = (obj as Pair?)!!.f89t
            if (f89t < j) {
                return -1
            }
            return if (f89t == j) 0 else 1
        }
    }

    //    fun shareImageLogo(context: Context,uriImage: Uri){
//        val intent = Intent()
//        intent.action = "android.intent.action.SEND"
//        intent.putExtra("android.intent.extra.STREAM", uriImage)
//        intent.putExtra(
//            "android.intent.extra.TEXT",
//            "${context.getString(R.string.share_text)} ${context.resources.getString(R.string.app_link)}"
//        )
//        intent.type = "image/jpeg"
//        context.startActivity(Intent.createChooser(intent, "Share Logo using"))
//    }
//    fun saveImage(
//        str: String?,
//        str2: String?,
//        bitmap: Bitmap,
//        context: Context,
//        imgSavedListener: ImgSavedListener
//    ) {
//        val externalStoragePublicDirectory =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        Log.d(
//            "DEBUG",
//            "Save Image Clicked " + externalStoragePublicDirectory + "   " + externalStoragePublicDirectory.exists()
//        )
//        val sb = StringBuilder()
//        sb.append(str)
//        sb.append("/")
//        sb.append(str2)
//        val file = File(externalStoragePublicDirectory, sb.toString())
//        Log.d("DEBUG", "File path " + file + "  " + file.exists())
//        try {
//            file.parentFile.mkdirs()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
//            MediaScannerConnection.scanFile(
//                context,
//                arrayOf(file.toString()),
//                null as Array<String?>?
//            ) { str, uri -> imgSavedListener?.onPictureSaved(uri) }
//            val contentValues = ContentValues()
//            contentValues.put("_data", file.absolutePath)
//            contentValues.put("mime_type", "image/jpeg")
//            context.contentResolver.insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            imgSavedListener?.onPictureSaved(null as Uri?)
//        }
//    }
//    fun saveImage(
//        str: String?,
//        str2: String?,
//        bitmap: Bitmap,
//        context: Context,
//        resolution: ImageResolution,
//        format: ImageFormat,
//        imgSavedListener: ImgSavedListener
//    ) {
//        val desiredWidth = resolution.width
//        val desiredHeight = resolution.height
//
//        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true)
//
//        val externalStoragePublicDirectory =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//
//        val formatExtension = when (format) {
//            ImageFormat.PNG -> "png"
//            ImageFormat.JPEG -> "jpg"
////            ImageFormat.JPEG -> "jpg"
//            ImageFormat.PDF -> "pdf"
//        }
//
//        val fileName = "$str/$str2"
//
//        val file = File(externalStoragePublicDirectory, fileName)
//        try {
//            file.parentFile.mkdirs()
//            val outputStream = FileOutputStream(file)
//            when (format) {
//                ImageFormat.PNG -> {
//                    resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                }
//
//                ImageFormat.JPEG -> {
//                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                }
//
//                ImageFormat.PDF -> {
//                    // Convert to PDF using a library or framework supporting PDF creation from images
//                    // Implement PDF conversion logic here
//                }
//            }
//            outputStream.flush()
//            outputStream.close()
//
//            MediaScannerConnection.scanFile(
//                context,
//                arrayOf(file.toString()),
//                null
//            ) { _, uri -> imgSavedListener.onPictureSaved(uri) }
//
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Images.Media.DATA, file.absolutePath)
//                put(MediaStore.Images.Media.MIME_TYPE, "image/$formatExtension")
//            }
//            context.contentResolver.insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            imgSavedListener.onPictureSaved(null)
//        }
//    }

    fun saveTempFile(bitmap: Bitmap?, file: File, context: Context?): String? {
        val path = file.path
        val file2 = File(path, "tempFiles/" + System.currentTimeMillis())
        return try {
            file2.parentFile.mkdirs()
            if (bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file2))) {
                file2.path
            } else null
        } catch (unused: Exception) {
            null
        }
    }

    fun removeTempFiles(context: Context) {
        val file = File(context.filesDir, "tempFiles")
        if (file.exists()) {
            val listFiles = file.listFiles()
            for (delete in listFiles) {
                delete.delete()
            }
        }
    }

    fun removeImage(str: String?) {
        val file = File(str)
        if (file.exists()) {
            file.delete()
        }
    }

//    fun getResIdOfStyle(view: View): Int {
//        return when (view.id) {
//            R.id.magicBrushStyle10 -> R.drawable.mg10
//            R.id.magicBrushStyle11 -> R.drawable.mg11
//            R.id.magicBrushStyle12 -> R.drawable.mg12
//            R.id.magicBrushStyle13 -> R.drawable.mg13
//            R.id.magicBrushStyle14 -> R.drawable.mg14
//            R.id.magicBrushStyle15 -> R.drawable.mg15
//            R.id.magicBrushStyle16 -> R.drawable.mg16
//            R.id.magicBrushStyle17 -> R.drawable.mg17
//            R.id.magicBrushStyle18 -> R.drawable.mg18
//            R.id.magicBrushStyle19 -> R.drawable.mg19
//            R.id.magicBrushStyle2 -> R.drawable.mg2
//            R.id.magicBrushStyle20 -> R.drawable.mg20
//            R.id.magicBrushStyle21 -> R.drawable.mg21
//            R.id.magicBrushStyle22 -> R.drawable.mg22
//            R.id.magicBrushStyle23 -> R.drawable.mg23
//            R.id.magicBrushStyle24 -> R.drawable.mg24
//            R.id.magicBrushStyle25 -> R.drawable.mg25
//            R.id.magicBrushStyle26 -> R.drawable.mg26
//            R.id.magicBrushStyle27 -> R.drawable.mg27
//            R.id.magicBrushStyle28 -> R.drawable.mg28
//            R.id.magicBrushStyle29 -> R.drawable.mg29
//            R.id.magicBrushStyle3 -> R.drawable.mg3
//            R.id.magicBrushStyle30 -> R.drawable.mg30
//            R.id.magicBrushStyle4 -> R.drawable.mg4
//            R.id.magicBrushStyle5 -> R.drawable.mg5
//            R.id.magicBrushStyle6 -> R.drawable.mg6
//            R.id.magicBrushStyle7 -> R.drawable.mg7
//            R.id.magicBrushStyle8 -> R.drawable.mg8
//            R.id.magicBrushStyle9 -> R.drawable.mg9
//            else -> R.drawable.mg1
//        }
//    }

    fun isToShowColorSlider(i: Int): Boolean {
        for (i2 in Const.NOT_COLORIZE) {
            if (i == i2) {
                return false
            }
        }
        return true
    }

    //    fun showCustomDialog(
//        context: Context,
//        layoutResId: Int,
//        title: String,
//        positiveButtonText: String,
//        negativeButtonText: String,
//        positiveButtonAction: () -> Unit,
//        negativeButtonAction: () -> Unit
//    ) {
//
//        val dialog = Dialog(context)
//        val window = dialog.window
////        window?.setSoftInputMode(32)
//        val inflate =
//            LayoutInflater.from(context).inflate(R.layout.dialog_back_create_logo, null as ViewGroup?)
//        dialog.setContentView(inflate)
//        window!!.setBackgroundDrawableResource(R.drawable.bg_transparent)
//        Objects.requireNonNull<Window?>(window)
//            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
//        val tvCancel = dialog.findViewById<TextView>(R.id.tvCancel)
//        val tvDontSave = dialog.findViewById<TextView>(R.id.tvDontSave)
//
//        tvTitle.text = title
//        tvDontSave.text = positiveButtonText
//        tvCancel.text = negativeButtonText
//        tvDontSave.setOnClickListener{
//            positiveButtonAction.invoke()
//            dialog.dismiss()
//        }
//
//        tvCancel.setOnClickListener {
//            negativeButtonAction.invoke()
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }
    @TargetApi(21)
    private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
        val createBitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(createBitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return createBitmap
    }

    private fun getBitmap(vectorDrawableCompat: VectorDrawableCompat): Bitmap {
        val createBitmap = Bitmap.createBitmap(
            vectorDrawableCompat.intrinsicWidth,
            vectorDrawableCompat.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(createBitmap)
        vectorDrawableCompat.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawableCompat.draw(canvas)
        return createBitmap
    }

    private fun getBitmap(vectorDrawableCompat: VectorDrawableCompat, i: Int, i2: Int): Bitmap {
        val createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        vectorDrawableCompat.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawableCompat.draw(canvas)
        return createBitmap
    }

    @TargetApi(21)
    private fun getBitmap(vectorDrawable: VectorDrawable, i: Int, i2: Int): Bitmap {
        val createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return createBitmap
    }

    fun getBitmap(context: Context, @DrawableRes i: Int, i2: Int, i3: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, i)
        if (drawable is BitmapDrawable) {
            return getImageSmallThanRequired(context.resources, i, i2, i3)
        }
        if (drawable is VectorDrawableCompat) {
            return getBitmap(drawable, i2, i3)
        }
        throw IllegalArgumentException("Unsupported drawable type")
    }

    fun getBitmap(context: Context?, @DrawableRes i: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context!!, i)
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        if (drawable is VectorDrawableCompat) {
            return getBitmap(drawable)
        }
        throw IllegalArgumentException("Unsupported drawable type")
    }

//    fun updateLocaleDataFromPrefrence(context: Context, activity: Activity): Boolean {
//        val sharedPreferences = context.getSharedPreferences(AppConstant.Companion.PREF_LANG, 0)
//        val trim = sharedPreferences.getString(AppConstant.Companion.PREF_KEY_LANGUAGE, "en")!!
//            .trim { it <= ' ' }
//        val trim2 = sharedPreferences.getString(AppConstant.Companion.PREF_KEY_COUNTRY, "US")!!
//            .trim { it <= ' ' }
//        val z = sharedPreferences.getBoolean(AppConstant.Companion.PREF_KEY_LANG_SET, false)
//        Log.d(
//            AppConstant.Companion.TAG,
//            "updateLocaleDataFromPrefrence: The Data got from the Locale is :- language -- $trim    country -- $trim2"
//        )
//        setLocale(activity, Locale(trim, trim2))
//        return z
//    }

    private fun setLocale(activity: Activity, locale: Locale) {
        val resources = activity.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, displayMetrics)
    }

//    fun findContextForPackage(context: Context, str: String): Context {
//        if (str == context.packageName) {
//            Log.d(AppConstant.Companion.TAG, "findContextForPackage: Returning the main Context")
//            return context
//        }
//        return try {
//            Log.d(
//                AppConstant.Companion.TAG,
//                "findContextForPackage: Trying to return the Context using createPackageContext"
//            )
//            context.createPackageContext(str, 0)
//        } catch (e: PackageManager.NameNotFoundException) {
//            Log.e(
//                AppConstant.Companion.TAG,
//                "findContextForPackage: Exception while creating packageContext -- $e"
//            )
//            if (str.contains(context.packageName)) {
//                Log.d(
//                    AppConstant.Companion.TAG,
//                    "findContextForPackage: package contains Context package name... Returning original Context"
//                )
//                return context
//            }
//            throw IllegalArgumentException("Failed to obtain context ", e)
//        }
//    }

    fun getBitmapFromAsset(
        assetManager: AssetManager,
        str: String?,
        options: BitmapFactory.Options?,
    ): Bitmap? {
        val e: Any
        val str2: String
        val stringBuilder: StringBuilder
        var th: Throwable
        var open: InputStream?

        Log.d(Const.TAG, "getBitmapFromAsset: str:$str")
        return try {
            var decodeStream: Bitmap?
            open = assetManager.open(str!!)
            if (options != null) {
                decodeStream = BitmapFactory.decodeStream(open, null, options)
            }
            decodeStream = BitmapFactory.decodeStream(open)
            var str3: String = Const.TAG
            var stringBuilder2 = StringBuilder()
            stringBuilder2.append("getBitmapFromAsset: After Loading Bitmap from AssetManager : -- ")
            stringBuilder2.append(decodeStream)
            Log.d(str3, stringBuilder2.toString())
            str3 = Const.TAG
            stringBuilder2 = StringBuilder()
            stringBuilder2.append("getBitmapFromAsset: Options : -- ")
            stringBuilder2.append(options)
            Log.d(str3, stringBuilder2.toString())
            if (open == null) {
                return decodeStream
            }
            try {
                open.close()
                decodeStream
            } catch (unused3: IOException) {
                decodeStream
            }
        } catch (e3: IOException) {
            e = e3
            open = null
            str2 = Const.TAG
            stringBuilder = StringBuilder()
            stringBuilder.append("getBitmapFromAsset: ")
            stringBuilder.append(e)
            Log.e(str2, stringBuilder.toString())
            if (open != null) {
            }
            null
        }
    }

    fun showHelpVideo(context: Context, i: Int, i2: Int) {
        val create = AlertDialog.Builder(context).create()
        create.setTitle(i)
        create.setMessage(context.getString(i2))
        create.setButton(
            -1,
            "ok"
        ) { dialogInterface, i -> dialogInterface.dismiss() }
        create.show()
    }
}