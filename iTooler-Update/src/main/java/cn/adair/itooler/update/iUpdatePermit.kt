package cn.adair.itooler.update

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

/**
 * 下载的权限申请
 * cn.adair.itooler.update
 * Created by Administrator on 2018/9/18/018.
 * slight negligence may lead to great disaster~
 */
object iUpdatePermit {

    fun _CheckUpdatePermit(iCtx: Context): Boolean {
        if (ContextCompat.checkSelfPermission(iCtx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        if (ContextCompat.checkSelfPermission(iCtx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

}