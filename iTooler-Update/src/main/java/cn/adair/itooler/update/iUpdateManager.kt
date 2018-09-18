package cn.adair.itooler.update

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * 下载管理
 * cn.adair.itooler.update
 * Created by Administrator on 2018/9/18/018.
 * slight negligence may lead to great disaster~
 */
class iUpdateManager {

    var TAG = "UpdateManager";

    lateinit var iContext: Context

    var iNoticeId: Int = 1001;
    var iNoticeIcon: Int = -1; // 通知icon
    var iNoticeTitle: String = "通知标题";
    var iNoticeContent: String = "通知内容";

    var iUpdateUri: String = "";// 文件下载地址
    var iUpdateName: String = "xxx.apk";// 文件名加后缀
    var iUpdatePath: String = "";// 文件保存路径

    companion object {

        @SuppressLint("StaticFieldLeak")
        var INSTANCE: iUpdateManager? = null

        fun _Instance(iCtx: Context): iUpdateManager {
            if (INSTANCE == null) {
                synchronized(iUpdateManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = iUpdateManager()
                    }
                }
            }
            INSTANCE!!.iContext = iCtx
            return INSTANCE!!
        }

        /**
         * 供此依赖库自己使用.
         * @return [DownloadManager]
         * @hide
         */
        fun _Instance(): iUpdateManager {
            if (INSTANCE == null) {
                throw RuntimeException("请先调用 getInstance(Context context) !")
            }
            return INSTANCE!!
        }
    }


    fun download() {
        if (!iUpdatePermit._CheckUpdatePermit(iContext)) {
            Log.d(TAG, "---->未开启读写权限")
            //没有权限,去申请权限
            iContext.startActivity(Intent(iContext, PermissionActivity::class.java))
            return
        }
        Log.d(TAG, "---->开始下载应用程序")
        iContext.startService(Intent(iContext, iUpdateService::class.java))
    }

}