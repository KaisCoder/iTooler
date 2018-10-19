package cn.adair.itooler.tooler

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import java.io.File

/**
 * cn.adair.itooler.tooler
 * Created by Administrator on 2018/10/19/019.
 * slight negligence may lead to great disaster~
 */
object iSPer {

    lateinit var mShPre: SharedPreferences
    var FILE_PATH: String = iFileer.isFilePath("data")

    fun init(context: Context) {
        mShPre = _GetSharedPreferences(context, "data")
    }

    /**
     * 反射修改SharedPreferences 数据保存路径
     * @param context
     * @param fileName
     * @return isDebug = 返回修改路径(路径不存在会自动创建)以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml<br></br>
     * !isDebug = 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
     */
    private fun _GetSharedPreferences(context: Context, fileName: String): SharedPreferences {
        try {
            // 获取ContextWrapper对象中的mBase变量。该变量保存了ContextImpl对象
            var field = ContextWrapper::class.java.getDeclaredField("mBase")
            field.isAccessible = true
            // 获取mBase变量
            val obj = field.get(context)
            // 获取ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            field = obj.javaClass.getDeclaredField("mPreferencesDir")
            field.isAccessible = true
            // 创建自定义路径
            val file = File(FILE_PATH)
            // 修改mPreferencesDir变量的值
            field.set(obj, file)
            // 返回修改路径以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml
            return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        // 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }


    /**
     * 保存数据
     *
     * @param keyName
     * @param value
     */
    fun set(keyName: String, value: Any?) {
        val editor = mShPre.edit()
        if (value is String) {
            editor.putString(keyName, value as String?)
        } else if (value is Int) {
            editor.putInt(keyName, (value as Int?)!!)
        } else if (value is Boolean) {
            editor.putBoolean(keyName, (value as Boolean?)!!)
        } else if (value is Float) {
            editor.putFloat(keyName, (value as Float?)!!)
        } else if (value is Long) {
            editor.putLong(keyName, (value as Long?)!!)
        } else {
            editor.putString(keyName, value?.toString())
        }
        editor.apply()
    }

}