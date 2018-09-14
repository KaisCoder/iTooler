package cn.adair.itooler.update

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v4.content.FileProvider
import android.widget.Toast
import cn.adair.itooler.notice.NoticeUtil
import cn.adair.itooler.tool.iLogger
import cn.adair.itooler.util.iFileUtil
import java.io.File

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/20/020.
 * slight negligence may lead to great disaster~
 */
class DownloadService : Service(), OnDownloadListener {

    lateinit var apkUri: String
    lateinit var apkName: String
    var smallIcon: Int = 0

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
        smallIcon = DownloadManager.getInstance().getSmallIcon()
        apkName = DownloadManager.getInstance().getApkName()
        apkUri = DownloadManager.getInstance().getApkUrl()

        var isOpen: Boolean = NoticeUtil.notificationEnable(this)
        iLogger.e(if (isOpen) "应用的通知栏开关状态：已打开" else "应用的通知栏开关状态：已关闭")
        download()
    }

    /**
     *  获取下线管理者
     */
    @Synchronized
    fun download() {
        HttpDownloadManager(this, iFileUtil.isFilePath("update")).download(apkUri, apkName, this)
    }

    override fun start() {
        handler.sendEmptyMessage(0)
        NoticeUtil.showNotification(this, smallIcon, "开始下载", "可稍后查看下载进度")
    }

    var lastProgress: Int = 0

    override fun downloading(max: Int, progress: Int) {
        val curr = (progress / max.toDouble() * 100.0).toInt()
        if (curr != lastProgress) {
            lastProgress = curr
            NoticeUtil.showProgressNotification(this, smallIcon, "医道", "正在下载$lastProgress%", max, progress)
        }
    }

    override fun done(apk: File) {
        NoticeUtil.showDoneNotification(this, smallIcon, "下载完成", "点击进行安装", packageName, apk)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this.getApplicationContext(), packageName + ".FileProvider", apk)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            uri = Uri.fromFile(apk)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        this.startActivity(intent)
        releaseResources()
    }

    override fun error(e: Exception) {
        NoticeUtil.showErrorNotification(this, smallIcon, "下载出错", "点击重新下载")
    }

    private fun releaseResources() {
        handler.removeCallbacksAndMessages(null)
        stopSelf()
        DownloadManager.getInstance().release()
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            Toast.makeText(this@DownloadService, "正在后台下载新版本...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }
}