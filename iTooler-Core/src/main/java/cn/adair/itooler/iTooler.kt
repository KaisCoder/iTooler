package cn.adair.itooler

import android.app.Application
import android.content.Context
import cn.adair.itooler.tooler.iUuider

/**
 * cn.adair.itool_kotlin
 * Created by Administrator on 2018/5/14/014.
 * slight negligence may lead to great disaster~
 */
object iTooler {

    var TAG = "iTooler"
    var isDebug = true

    lateinit var iCtx: Application

    fun init(context: Application): iTooler {
        iCtx = context
        return this
    }

    fun isDebug(isDebug: Boolean, tag: String): iTooler {
        iTooler.isDebug = isDebug
        TAG = tag
        return this
    }

    /**
     * 初始化其他
     */
    fun initOther(context: Context): iTooler {
        iUuider(context)
        return this
    }

}
