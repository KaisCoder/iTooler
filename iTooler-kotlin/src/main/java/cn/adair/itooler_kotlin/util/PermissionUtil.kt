package cn.adair.itooler_kotlin.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat

/**
 * cn.adair.itooler_kotlin.util
 * Created by Administrator on 2018/7/24/024.
 * slight negligence may lead to great disaster~
 */
object PermissionUtil {

    /**
     * 检查存储空间权限
     * @return
     */
    fun checkStoragePermission(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }

    /**
     * 请求这个库需要的权限
     * @return
     */
    fun requestPermission(activity: Activity, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
    }

    /**
     * 检查Android O 应用安装权限
     *
     * @return false 没有权限，需要申请
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun checkOreoInstallPermission(context: Context): Boolean {
        return context.packageManager.canRequestPackageInstalls()
    }

    /**
     * 检查通知权限
     *
     * @return false 没有权限，需要申请
     */
    fun checkNotificationPermission(context: Context): Boolean {
        val managerCompat = NotificationManagerCompat.from(context)
        return managerCompat.areNotificationsEnabled()
    }
}