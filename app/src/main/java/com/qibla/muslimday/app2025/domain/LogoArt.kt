package com.qibla.muslimday.app2025.domain

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.Keep
import com.google.gson.Gson
import com.qibla.muslimday.app2025.domain.draw.BGClassInfo
import com.qibla.muslimday.app2025.domain.draw.DataJSONConst
import com.qibla.muslimday.app2025.domain.draw.FilterClassInfo
import com.qibla.muslimday.app2025.domain.draw.LogoArtInfo
import com.qibla.muslimday.app2025.domain.draw.LogoDesignCombInfo
import com.qibla.muslimday.app2025.domain.draw.StickersDataClass
import com.qibla.muslimday.app2025.domain.draw.TextDataClass
import com.qibla.muslimday.app2025.domain.draw.TouchDataClass
import com.qibla.muslimday.app2025.util.Const
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

@Keep
object LogoArt : DataJSONConst {
    var DATA_FOLDER_NAME: String = "/Maker/"
    fun saveNameArt(
        context: Context?,
        logoDesignCombInfo: LogoDesignCombInfo,
        str: String
    ): String? {
        var str: String = str
        var stringBuilder: StringBuilder
        val th: Throwable
        var fileOutputStream: FileOutputStream?
        var e: Throwable
        val stringBuilder2: StringBuilder = StringBuilder()
        stringBuilder2.append(str)
        stringBuilder2.append(".txt")
        var stringBuilder3: String? = stringBuilder2.toString()
        val gson: Gson = Gson()
        val toJson: String = gson.toJson(logoDesignCombInfo.f87bg, BGClassInfo::class.java as Type?)
        val toJson2: String =
            gson.toJson(logoDesignCombInfo.filter, FilterClassInfo::class.java as Type?)
        val toJson3: String =
            gson.toJson(logoDesignCombInfo.logoArtInfo, LogoArtInfo::class.java as Type?)
        val strArr: Array<String?> = arrayOfNulls(
            logoDesignCombInfo.stickers!!.size
        )
        for (i in logoDesignCombInfo.stickers!!.indices) {
            strArr[i] = gson.toJson(
                logoDesignCombInfo.stickers!!.get(i),
                StickersDataClass::class.java as Type?
            )
        }
        val strArr2: Array<String?> = arrayOfNulls(
            logoDesignCombInfo.texts!!.size
        )
        for (i2 in logoDesignCombInfo.texts!!.indices) {
            strArr2[i2] =
                gson.toJson(logoDesignCombInfo.texts!![i2], TextDataClass::class.java as Type?)
        }
        val strArr3 = arrayOfNulls<String>(logoDesignCombInfo.touchDataClasses!!.size)
        for (i3 in 0 until logoDesignCombInfo.touchDataClasses!!.size) {
            strArr3[i3] = gson.toJson(
                logoDesignCombInfo.touchDataClasses!![i3],
                TouchDataClass::class.java as Type
            )
        }
        val jSONObject: JSONObject = JSONObject()
        try {
            jSONObject.put(DataJSONConst.ART_INFO_JSON, toJson3)
            jSONObject.put(DataJSONConst.BG_JSON_STRING, toJson)
            jSONObject.put(DataJSONConst.FILTER_JSON, toJson2)
            var jSONArray: JSONArray = JSONArray()
            var i4: Int = 0
            while (i4 < logoDesignCombInfo.stickers!!.size) {
                jSONArray.put(strArr[i4])
                i4++
            }
            jSONObject.put(DataJSONConst.Companion.STICKERS_JSON_ARRAY, jSONArray)
            jSONArray = JSONArray()
            i4 = 0
            while (i4 < logoDesignCombInfo.texts!!.size) {
                jSONArray.put(strArr2[i4])
                i4++
            }
            jSONObject.put(DataJSONConst.Companion.TEXT_JSON_ARRAY, jSONArray)
            jSONArray = JSONArray()
            for (i5 in logoDesignCombInfo.touchDataClasses!!.indices) {
                jSONArray.put(strArr3[i5])
            }
            jSONObject.put(DataJSONConst.Companion.TOUCH_JSON_ARRAY, jSONArray)
        } catch (e2: JSONException) {
            e2.printStackTrace()
        }
        var fileOutputStream2: FileOutputStream? = null
        try {
            var stringBuilder4: StringBuilder
            val file: File
            Log.d("ntt", "About to open output stream: ")
            if (Build.VERSION.SDK_INT >= 19) {
                stringBuilder4 = StringBuilder()
                stringBuilder4.append(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .absolutePath
                )
                stringBuilder4.append(DATA_FOLDER_NAME)
                stringBuilder4.append(stringBuilder3)
                file = File(stringBuilder4.toString())
            } else {
                stringBuilder4 = StringBuilder()
                stringBuilder4.append(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .absolutePath
                )
                stringBuilder4.append(DATA_FOLDER_NAME)
                stringBuilder4.append(stringBuilder3)
                file = File(stringBuilder4.toString())
            }
            stringBuilder3 = Const.TAG
            stringBuilder4 = StringBuilder()
            stringBuilder4.append("saveNameArt: saving file at : ")
            stringBuilder4.append(file.absolutePath)
            Log.d(stringBuilder3, stringBuilder4.toString())
            if (!file.exists()) {
                file.getParentFile().mkdirs()
            }
            val fileOutputStream3: FileOutputStream = FileOutputStream(file)
            try {
                str = jSONObject.toString()
                fileOutputStream3.write(str.toByteArray())
                fileOutputStream3.flush()
                fileOutputStream3.close()
                try {
                    fileOutputStream3.close()
                } catch (e3: Exception) {
                    val str2: String = Const.TAG
                    stringBuilder = StringBuilder()
                    stringBuilder.append("saveNameArt: ")
                    stringBuilder.append(e3)
                    Log.e(str2, stringBuilder.toString())
                }
                return str
            } catch (e4: IOException) {
                th = e4
                fileOutputStream = fileOutputStream3
                e = th
                try {
                    Log.e("ntt", "saveJson: ", e)
                    e.printStackTrace()
                    try {
                        fileOutputStream.close()
                    } catch (e32: Exception) {
                        str = Const.TAG
                        stringBuilder = StringBuilder()
                        stringBuilder.append("saveNameArt: ")
                        stringBuilder.append(e32)
                        Log.e(str, stringBuilder.toString())
                    }
                    return null
                } catch (th2: Throwable) {
                    e = th2
                    fileOutputStream2 = fileOutputStream
                    try {
                        fileOutputStream2.close()
                    } catch (e5: Exception) {
                        val stringBuilder5: StringBuilder = StringBuilder()
                        stringBuilder5.append("saveNameArt: ")
                        stringBuilder5.append(e5)
                        Log.e(Const.TAG, stringBuilder5.toString())
                    }
                }
            }
        } catch (e6: IOException) {
            e = e6
            fileOutputStream = null
            Log.e("ntt", "IOException saveJson: ", e)
            e.printStackTrace()
            try {
                fileOutputStream?.close()
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
            return null
        }
        return stringBuilder3
    }

    fun getNameArt(context: Context, str: String?, fromAsset: Boolean): LogoDesignCombInfo? {
        var str: String? = str
        var stringBuilder: StringBuilder
        if (!str!!.endsWith(".txt")) {
            stringBuilder = StringBuilder()
            stringBuilder.append(str)
            stringBuilder.append(".txt")
            str = stringBuilder.toString()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            str = stringBuilder.toString()
        } else {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            str = stringBuilder.toString()
        }
        val str2: String = Const.TAG
        val stringBuilder2: StringBuilder = StringBuilder()
        stringBuilder2.append("getNameArt: checking for file : ")
        stringBuilder2.append(str)
        return getNameArt(context, str, null, fromAsset)
    }

    fun getNameArt(
        context: Context,
        str1: String?,
        assetManager: AssetManager?,
        fromAsset: Boolean
    ): LogoDesignCombInfo? {
        var str: String? = str1
        str = if (fromAsset) {
            getJSONDataForFile(context, str!!, assetManager)
        } else {
            getJSONDataForFile(context, str!!)
        }

        var i: Int = 0
        if (str.isNullOrEmpty()) {
            Toast.makeText(
                context,
                "Data not found",
                Toast.LENGTH_SHORT
            ).show()
            return null
        }
        try {
            val gson: Gson = Gson()
            val logoDesignCombInfo: LogoDesignCombInfo = LogoDesignCombInfo()
            val jSONObject: JSONObject = JSONObject(str)
            val jSONArray: JSONArray =
                jSONObject.getJSONArray(DataJSONConst.Companion.TEXT_JSON_ARRAY)
            val jSONArray2: JSONArray =
                jSONObject.getJSONArray(DataJSONConst.Companion.STICKERS_JSON_ARRAY)
            val jSONArray3: JSONArray =
                jSONObject.getJSONArray(DataJSONConst.Companion.TOUCH_JSON_ARRAY)
            logoDesignCombInfo.f87bg = BGClassInfo()
            logoDesignCombInfo.f87bg = gson.fromJson<BGClassInfo>(
                jSONObject.getString(DataJSONConst.Companion.BG_JSON_STRING),
                BGClassInfo::class.java
            )
            logoDesignCombInfo.filter = FilterClassInfo()
            logoDesignCombInfo.filter = gson.fromJson<FilterClassInfo>(
                jSONObject.getString(DataJSONConst.Companion.FILTER_JSON),
                FilterClassInfo::class.java
            )
            logoDesignCombInfo.texts = arrayOfNulls(jSONArray.length())
            for (i2 in logoDesignCombInfo.texts!!.indices) {
                logoDesignCombInfo.texts!![i2] =
                    gson.fromJson(jSONArray.getString(i2), TextDataClass::class.java)
            }
            logoDesignCombInfo.stickers = arrayOfNulls(jSONArray2.length())
            for (i3 in logoDesignCombInfo.stickers!!.indices) {
                logoDesignCombInfo.stickers!![i3] =
                    gson.fromJson(jSONArray2.getString(i3), StickersDataClass::class.java)

                Log.d(
                    "ntt",
                    "getNameArt: ${logoDesignCombInfo.stickers!![i3]?.imagePath.toString()}"
                )
            }
            logoDesignCombInfo.touchDataClasses = arrayOfNulls(jSONArray3.length())
            while (i < logoDesignCombInfo.touchDataClasses!!.size) {
                logoDesignCombInfo.touchDataClasses!![i] =
                    gson.fromJson(jSONArray3.getString(i), TouchDataClass::class.java)
                i++
            }
            logoDesignCombInfo.logoArtInfo = gson.fromJson<LogoArtInfo>(
                jSONObject.getString(DataJSONConst.Companion.ART_INFO_JSON),
                LogoArtInfo::class.java
            )
            return logoDesignCombInfo
        } catch (e: Exception) {
            return null
        }
    }

    fun changeFileExtension(fileName: String, newExtension: String): String {
        val lastDot = fileName.lastIndexOf('.')
        if (lastDot >= 0) {
            val fileNameWithoutExtension = fileName.substring(0, lastDot)
            return "$fileNameWithoutExtension.$newExtension"
        }
        return fileName // Trả về tên file không thay đổi nếu không tìm thấy dấu chấm trong tên file
    }

    fun getJSONDataForFile(context: Context?, str: String?, assetManager: AssetManager?): String? {
        var json: String? = null
        try {
            val `is`: InputStream = assetManager!!.open((str)!!)
            val size: Int = `is`.available()
            val buffer: ByteArray = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getJSONDataForFile(context: Context?, fileName: String): String? {
        var json: String? = null
        try {
            val filePath: String =
                context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + File.separator + fileName
            val file = File(fileName)
            val inputStream: InputStream = FileInputStream(file)
            val size: Int = inputStream.available()
            val buffer: ByteArray = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getJSONDataForFileName(
        context: Context?,
        str: String?,
        assetManager: AssetManager?
    ): String {
        var str: String? = str
        var stringBuilder: StringBuilder
        val stringBuilder2: String
        var stringBuilder3: StringBuilder? = null
        var stringBuilder4: StringBuilder
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader?
        var e: Any?
        var str2: String?
        var stringBuilder5: StringBuilder
        var str3: String?
        var e2: Any?
        var stringBuilder6: StringBuilder
        var th: Throwable?
        var bufferedReader2: BufferedReader
        var str4: String?
        var e3: Any?
        var th2: Throwable?
        if (!str!!.endsWith(".txt")) {
            stringBuilder = StringBuilder()
            stringBuilder.append(str)
            stringBuilder.append(".txt")
            str = stringBuilder.toString()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            stringBuilder2 = stringBuilder.toString()
        } else {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            stringBuilder2 = stringBuilder.toString()
        }
        var stringBuilder7: StringBuilder? = null
        if (assetManager != null) {
            try {
                stringBuilder3 = StringBuilder()
                val open: InputStream = assetManager.open(stringBuilder2)
                try {
                    val bufferedReader3: BufferedReader =
                        BufferedReader(InputStreamReader(open, "UTF-8"))
                    while (true) {
                        try {
                            str = bufferedReader3.readLine()
                            if (str != null) {
                                stringBuilder3!!.append(str)
                            } else {
                                try {
                                    break
                                } catch (e4: Exception) {
                                    str = Const.TAG
                                    stringBuilder4 = StringBuilder()
                                    stringBuilder4.append("getNameArt: ")
                                    stringBuilder4.append(e4)
                                    Log.e(str, stringBuilder4.toString())
                                    e4.printStackTrace()
                                }
                            }
                        } catch (e5: IOException) {
                            val iOException: IOException = e5
                            inputStream = open
                            bufferedReader = bufferedReader3
                            e = iOException
                            try {
                                str2 = Const.TAG
                                stringBuilder5 = StringBuilder()
                                stringBuilder5.append("getNameArt: ")
                                stringBuilder5.append(e)
                                Log.e(str2, stringBuilder5.toString())
                                try {
                                    inputStream.close()
                                } catch (e6: Exception) {
                                    str3 = Const.TAG
                                    stringBuilder4 = StringBuilder()
                                    stringBuilder4.append("getNameArt: ")
                                    stringBuilder4.append(e6)
                                    Log.e(str3, stringBuilder4.toString())
                                    e6.printStackTrace()
                                }
                                try {
                                    bufferedReader.close()
                                } catch (e7: Exception) {
                                    e2 = e7
                                    str = Const.TAG
                                    stringBuilder6 = StringBuilder()
                                }
                                stringBuilder7 = stringBuilder3
                                if (stringBuilder7 != null) {
                                }
                            } catch (th3: Throwable) {
                                th = th3
                                try {
                                    inputStream.close()
                                } catch (e62: Exception) {
                                    stringBuilder3 = StringBuilder()
                                    stringBuilder3.append("getNameArt: ")
                                    stringBuilder3.append(e62)
                                    Log.e(Const.TAG, stringBuilder3.toString())
                                    e62.printStackTrace()
                                }
                                try {
                                    bufferedReader.close()
                                } catch (e42: Exception) {
                                    stringBuilder7 = StringBuilder()
                                    stringBuilder7.append("getNameArt: ")
                                    stringBuilder7.append(e42)
                                    Log.e(Const.TAG, stringBuilder7.toString())
                                    e42.printStackTrace()
                                }
                                throw th
                            }
                        } catch (th4: Throwable) {
                            val th5: Throwable = th4
                            inputStream = open
                            bufferedReader = bufferedReader3
                            th = th5
                            inputStream.close()
                            bufferedReader.close()
                            throw th
                        }
                    }
                    open.close()
                    try {
                        bufferedReader3.close()
                    } catch (e8: Exception) {
                        e2 = e8
                        str = Const.TAG
                        stringBuilder6 = StringBuilder()
                    }
                } catch (e9: IOException) {
                    e = e9
                    inputStream = open
                    bufferedReader = null
                    str2 = Const.TAG
                    stringBuilder5 = StringBuilder()
                    stringBuilder5.append("getNameArt: ")
                    stringBuilder5.append(e)
                    Log.e(str2, stringBuilder5.toString())
                    inputStream.close()
                    bufferedReader?.close()
                    stringBuilder7 = stringBuilder3
                    if (stringBuilder7 != null) {
                    }
                } catch (th6: Throwable) {
                    th = th6
                    inputStream = open
                    bufferedReader = null
                    inputStream.close()
                    bufferedReader?.close()
                    throw th
                }
            } catch (e10: IOException) {
                e = e10
                bufferedReader = null
                str2 = Const.TAG
                stringBuilder5 = StringBuilder()
                stringBuilder5.append("getNameArt: ")
                stringBuilder5.append(e)
                Log.e(str2, stringBuilder5.toString())
                stringBuilder7 = stringBuilder3
                if (stringBuilder7 != null) {
                }
            } catch (th7: Throwable) {
            }
            stringBuilder7 = stringBuilder3
        } else {
            val file: File = File(stringBuilder2)
            if (file.exists()) {
                stringBuilder = StringBuilder()
                try {
                    val bufferedReader4: BufferedReader =
                        BufferedReader(FileReader(file.absolutePath))
                    while (true) {
                        try {
                            str = bufferedReader4.readLine()
                            if (str == null) {
                                break
                            }
                            stringBuilder.append(str)
                        } catch (e622: Exception) {
                            e = e622
                            bufferedReader2 = bufferedReader4
                            try {
                                str4 = Const.TAG
                                stringBuilder4 = StringBuilder()
                                stringBuilder4.append("getNameArt: ")
                                stringBuilder4.append(e)
                                Log.e(str4, stringBuilder4.toString())
                                try {
                                    bufferedReader2.close()
                                } catch (e11: IOException) {
                                    e3 = e11
                                    str3 = Const.TAG
                                    stringBuilder3 = StringBuilder()
                                }
                                stringBuilder7 = stringBuilder
                                if (stringBuilder7 != null) {
                                }
                            } catch (th8: Throwable) {
                                try {
                                    bufferedReader2.close()
                                } catch (e52: IOException) {
                                    stringBuilder6 = StringBuilder()
                                    stringBuilder6.append("getNameArt: ")
                                    stringBuilder6.append(e52)
                                    Log.e(Const.TAG, stringBuilder6.toString())
                                    e52.printStackTrace()
                                }
                                throw th8
                            }
                        } catch (th9: Throwable) {
                            bufferedReader2 = bufferedReader4
                            bufferedReader2.close()
                            throw th9
                        }
                    }
                    bufferedReader4.close()
                    try {
                        bufferedReader4.close()
                    } catch (e12: IOException) {
                        e3 = e12
                        str3 = Const.TAG
                        stringBuilder3 = StringBuilder()
                    }
                } catch (e13: Exception) {
                    e = e13
                    str4 = Const.TAG
                    stringBuilder4 = StringBuilder()
                    stringBuilder4.append("getNameArt: ")
                    stringBuilder4.append(e)
                    Log.e(str4, stringBuilder4.toString())
                    stringBuilder7 = stringBuilder
                    if (stringBuilder7 != null) {
                    }
                }
                stringBuilder7 = stringBuilder
            }
        }
        return if ((stringBuilder7 != null || stringBuilder7?.length!! <= 0)) "" else stringBuilder7.toString()
    }

    fun isNameArtExist(context: Context?, str: String?): Boolean {
        var str: String? = str
        var stringBuilder: StringBuilder
        val stringBuilder2: String
        if (!str!!.endsWith(".txt")) {
            stringBuilder = StringBuilder()
            stringBuilder.append(str)
            stringBuilder.append(".txt")
            str = stringBuilder.toString()
        }
        if (Build.VERSION.SDK_INT >= 19) {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            stringBuilder2 = stringBuilder.toString()
        } else {
            stringBuilder = StringBuilder()
            stringBuilder.append(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath
            )
            stringBuilder.append(DATA_FOLDER_NAME)
            stringBuilder.append(str)
            stringBuilder2 = stringBuilder.toString()
        }
        str = Const.TAG
        val stringBuilder3: StringBuilder = StringBuilder()
        stringBuilder3.append("isNameArtExist: checking for file at path :  ")
        stringBuilder3.append(stringBuilder2)
        Log.d(str, stringBuilder3.toString())
        return File(stringBuilder2).exists()
    }
}