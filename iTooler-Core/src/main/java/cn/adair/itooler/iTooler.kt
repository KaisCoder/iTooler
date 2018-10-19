package cn.adair.itooler

import android.app.Application
import cn.adair.itooler.tooler.iSPer
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

    fun init(context: Application, debug: Boolean): iTooler {
        iCtx = context
        isDebug = debug
        iUuider(context)
        return this
    }

    fun initOther(tag: String): iTooler {
        TAG = tag
        iSPer.init(iCtx)
        return this;
    }

}
