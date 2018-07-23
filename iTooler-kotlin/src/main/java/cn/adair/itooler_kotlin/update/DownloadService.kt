package cn.adair.itooler_kotlin.update

import android.app.Service
import android.content.Intent
import android.os.IBinder
import cn.adair.itooler_kotlin.notice.NoticeUtil
import cn.adair.itooler_kotlin.tool.iLogger
import java.io.File

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/20/020.
 * slight negligence may lead to great disaster~
 */
class DownloadService : Service(), OnDownloadListener {

    lateinit var apkUri: String
    lateinit var apkName: String

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (null == intent) {
            return Service.START_STICKY
        }
        initService()
        return super.onStartCommand(intent, flags, startId)

    }

    /**
     * 初始化下载服务
     */
    fun initService() {
        val manager = DownloadManager.getInstance(this)
        apkName = "YiDao.apk"
        apkUri = "http://files.yidao.pro/admin/20180717/8b2649dcc2bc0ef3d7f1ecb4a1e8efae.apk"

        var isOpen: Boolean = NoticeUtil.notificationEnable(this)
        iLogger.e(if (isOpen) "应用的通知栏开关状态：已打开" else "应用的通知栏开关状态：已关闭")
        download()
    }

    /**
     *
     */
    fun download(){

     }


    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun downloading(max: Int, progress: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun done(apk: File) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(e: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }
}