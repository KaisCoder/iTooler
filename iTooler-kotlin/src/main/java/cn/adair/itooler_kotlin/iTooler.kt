package cn.adair.itooler_kotlin

import android.content.Context

/**
 * cn.adair.itool_kotlin
 * Created by Administrator on 2018/5/14/014.
 * slight negligence may lead to great disaster~
 */
object iTooler {

    var TAG = "iTooler"
    var isDebug = true

    lateinit var iCtx: Context

    fun init(context: Context): iTooler {
        this.iCtx = context
        return this
    }

    fun isDebug(isDebug: Boolean): iTooler {
        this.isDebug = isDebug
        return this
    }

}
