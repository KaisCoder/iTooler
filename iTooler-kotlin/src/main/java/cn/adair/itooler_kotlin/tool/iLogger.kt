package cn.adair.itooler_kotlin.tool

import android.text.TextUtils
import android.util.Log
import cn.adair.itooler_kotlin.iTooler

/**
 * cn.adair.itool_kotlin.tool
 * Created by Administrator on 2018/5/14/014.
 * slight negligence may lead to great disaster~
 */
object iLogger {

    fun d() {
        if (iTooler.isDebug) Log.d(iTooler.TAG, GetLocation())
    }

    fun d(msg: String) {
        if (iTooler.isDebug) Log.d(iTooler.TAG, GetLocation() + msg)
    }

    fun i() {
        if (iTooler.isDebug) Log.i(iTooler.TAG, GetLocation())
    }

    fun i(msg: String) {
        if (iTooler.isDebug) Log.i(iTooler.TAG, GetLocation() + msg)
    }

    fun e() {
        if (iTooler.isDebug) Log.e(iTooler.TAG, GetLocation())
    }

    fun e(msg: String) {
        if (iTooler.isDebug) Log.e(iTooler.TAG, GetLocation() + msg)
    }

    private fun GetLocation(): String {
        var clazName = iLogger::class.java.name
        val traces = Thread.currentThread().stackTrace
        var found = false
        for (trace in traces) {
            if (found) {
                if (!trace.className.startsWith(clazName)) {
                    val clazz = Class.forName(trace.className)
                    return "[" + GetClassName(clazz) + ":" + trace.methodName + ":" + trace.lineNumber + "]:"
                }
            } else if (trace.className.startsWith(clazName)) {
                found = true
            }
        }
        return "[]: "
    }

    private fun GetClassName(clazz: Class<*>?): String {
        if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.simpleName)) {
                return clazz.simpleName
            }
            return GetClassName(clazz.enclosingClass)
        }
        return ""
    }


}