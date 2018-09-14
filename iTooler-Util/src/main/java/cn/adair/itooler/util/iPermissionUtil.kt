package cn.adair.itooler.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import java.util.*

/**
 * 权限组列表：
 * Android6.0只用申请权限组中一个权限及获得全部权限
 * Android8.0需要全部申请权限组权限，但是只会申请第一个权限时提示，后面不会提示
 * <p>
 * // 读写日历。
 * Manifest.permission.READ_CALENDAR,
 * Manifest.permission.WRITE_CALENDAR
 * // 相机。
 * Manifest.permission.CAMERA
 * // 读写联系人。
 * Manifest.permission.READ_CONTACTS,
 * Manifest.permission.WRITE_CONTACTS,
 * Manifest.permission.GET_ACCOUNTS
 * // 读位置信息。
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.ACCESS_COARSE_LOCATION
 * // 使用麦克风。
 * Manifest.permission.RECORD_AUDIO
 * // 读电话状态、打电话、读写电话记录。
 * Manifest.permission.READ_PHONE_STATE,
 * Manifest.permission.CALL_PHONE,
 * Manifest.permission.READ_CALL_LOG,
 * Manifest.permission.WRITE_CALL_LOG,
 * Manifest.permission.ADD_VOICEMAIL,
 * Manifest.permission.USE_SIP,
 * Manifest.permission.PROCESS_OUTGOING_CALLS
 * // 传感器。
 * Manifest.permission.BODY_SENSORS
 * // 读写短信、收发短信。
 * Manifest.permission.SEND_SMS,
 * Manifest.permission.RECEIVE_SMS,
 * Manifest.permission.READ_SMS,
 * Manifest.permission.RECEIVE_WAP_PUSH,
 * Manifest.permission.RECEIVE_MMS,
 * Manifest.permission.READ_CELL_BROADCASTS
 * // 读写存储卡。
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE
 *
 * cn.adair.itooler.util
 * Created by Administrator on 2018/9/14/014.
 * slight negligence may lead to great disaster~
 */
object iPermissionUtil {

    var mRequestCode = -1

    lateinit var mOnPermissionListener: OnPermissionListener

    interface OnPermissionListener {

        abstract fun onPermissionGranted()

        abstract fun onPermissionDenied()

    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissions(context: Context, requestCode: Int, permissions: Array<String>, listener: OnPermissionListener) {
        if (context is Activity) {
            mOnPermissionListener = listener
            val deniedPermissions = getDeniedPermissions(context, *permissions)
            if (deniedPermissions.isNotEmpty()) {
                mRequestCode = requestCode
                context.requestPermissions(deniedPermissions.toTypedArray(), requestCode)
            } else {
                if (mOnPermissionListener != null) {
                    mOnPermissionListener.onPermissionGranted()
                }
            }
        } else {
            throw RuntimeException("Context must be an Activity")
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private fun getDeniedPermissions(context: Context, vararg permissions: String): List<String> {
        val deniedPermissions = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                if (verifyPermissions(grantResults)) {
                    mOnPermissionListener.onPermissionGranted()
                } else {
                    mOnPermissionListener.onPermissionDenied()
                }
            }
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private fun verifyPermissions(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 显示提示对话框
     */
    fun showTipsDialog(context: Context) {
        AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        startAppSettings(context)
                    }
                }).show()
    }

    /**
     * 启动当前应用设置页面
     */
    private fun startAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }


}