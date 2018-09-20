package cn.adair.itooler.tooler

import android.widget.Toast
import cn.adair.itooler.iTooler

/**
 * 吐司
 * cn.adair.itool_kotlin.toast
 * Created by Administrator on 2018/5/14/014.
 * slight negligence may lead to great disaster~
 */
object iToaster {

    fun showShort(message: CharSequence) {
        Toast.makeText(iTooler.iCtx, message, Toast.LENGTH_SHORT).show()
    }

    fun showLong(message: CharSequence) {
        Toast.makeText(iTooler.iCtx, message, Toast.LENGTH_LONG).show()
    }

}