package cn.adair.itooler.update

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import cn.adair.itooler.tool.iLogger
import cn.adair.itooler.tool.iToaster

/**
 * app 更新权限弹窗页面
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/20/020.
 * slight negligence may lead to great disaster~
 */
class PermissionActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 2014

    /**
     * 请求读外部存储的权限
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                iLogger.e("权限请求回调：grantResults isEmpty!!!")
            } else {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授予了存储空间权限
                    download()
                } else {
                    // 拒绝了存储空间权限
                    iToaster.showShort("请允许使用[存储空间]权限!")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //用户勾选了不在询问
                            jumpToAPPSetting(this)
                        }
                    }
                }
            }
        }
    }

    /**
     * 开始下载
     */
    fun download() {
        iToaster.showShort("开始下载")
        startService(Intent(this, DownloadService::class.java))
    }

    /**
     * 跳转到App 权限设置页面
     */
    fun jumpToAPPSetting(context: Context) {
        var intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(intent)
    }

}