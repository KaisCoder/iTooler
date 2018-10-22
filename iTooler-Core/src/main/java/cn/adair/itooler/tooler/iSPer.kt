package cn.adair.itooler.tooler

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * cn.adair.itooler.tooler
 * Created by Administrator on 2018/10/19/019.
 * slight negligence may lead to great disaster~
 */
object iSPer {

    private lateinit var mShPre: SharedPreferences
    private var FILE_PATH: String = iFileer.isFilePath("data")

    fun init(context: Context) {
        mShPre = _GetSharedPreferences(context, "data")
    }

    /**
     * 反射修改SharedPreferences 数据保存路径
     * 新的数据保存路径需要权限，如无权限数据路径依然在无法修改
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
     */
    fun _Put(keyName: String, value: Any?) {
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
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 获取数据
     */
    fun _Get(keyName: String, defaultValue: Any): Any? {
        val sp = mShPre
        if (defaultValue is String) {
            return sp.getString(keyName, defaultValue)
        } else if (defaultValue is Int) {
            return sp.getInt(keyName, defaultValue)
        } else if (defaultValue is Boolean) {
            return sp.getBoolean(keyName, defaultValue)
        } else if (defaultValue is Float) {
            return sp.getFloat(keyName, defaultValue)
        } else if (defaultValue is Long) {
            return sp.getLong(keyName, defaultValue)
        }
        return null
    }

    /**
     * 移除数据
     */
    fun _Remove(keyName: String) {
        val editor = mShPre.edit()
        editor.remove(keyName)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     */
    fun _Clear() {
        val editor = mShPre.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     */
    fun _Exist(keyName: String): Boolean {
        return mShPre.contains(keyName)
    }

    /**
     * 返回所有的键值对
     */
    fun _GetAll(): Map<String, *> {
        return mShPre.all
    }

    /**
     *  创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private object SharedPreferencesCompat {

        private val mApplyMethod = findApplyMethod()
        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (mApplyMethod != null) {
                    mApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }
            editor.commit()
        }
    }

}