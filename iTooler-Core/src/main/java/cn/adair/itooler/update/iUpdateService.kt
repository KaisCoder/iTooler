package cn.adair.itooler.update

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.support.v4.content.FileProvider
import android.util.Log
import cn.adair.itooler.notice.iNoticeConfig
import cn.adair.itooler.notice.iNoticeUtil
import java.io.File

/**
 * 更新服务
 * cn.adair.itooler.update
 * Created by Administrator on 2018/9/18/018.
 * slight negligence may lead to great disaster~
 */
class iUpdateService : Service(), OnUpdateListener {

    var TAG = "iUpdateService"

    lateinit var iUri: String
    lateinit var iPath: String
    lateinit var iName: String

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (null == intent) {
            return Service.START_STICKY
        }
        initService()
        return super.onStartCommand(intent, flags, startId)
    }

    lateinit var iConfig: iNoticeConfig

    fun initService() {
        // 更新配置
        iUri = iUpdateManager._Instance().iUpdateUri
        iPath = iUpdateManager._Instance().iUpdatePath
        iName = iUpdateManager._Instance().iUpdateName
        // 通知配置
        iConfig = iNoticeConfig()
        iConfig.iChannelId = "update"
        iConfig.iChannelName = "应用更新"
        iConfig.iNoticeId = iUpdateManager._Instance().iNoticeId
        iConfig.iNoticeTitle = iUpdateManager._Instance().iNoticeTitle
        iConfig.iNoticeContent = iUpdateManager._Instance().iNoticeContent

        val isOpen: Boolean = iNoticeUtil.iNoticeIsEnable(this)
        Log.d(TAG, if (isOpen) "应用的通知栏开关状态：已打开" else "应用的通知栏开关状态：已关闭")
        download()
    }

    /**
     * 下载
     * 获取下线管理者
     */
    @Synchronized
    fun download() {
        iUpdateDownload(this, iPath).download(iUri, iName, this)
    }

    /**
     * 开始下载
     */
    override fun start() {
        iNoticeUtil._SetConfig(iConfig).iNoticeShow(this, iUpdateManager._Instance().iNoticeIcon)
    }

    // 当前下载进度
    var lastProgress: Int = 0

    /**
     * 正在下载
     */
    override fun downloading(max: Int, progress: Int) {
        val curr = (progress / max.toDouble() * 100.0).toInt()
        if (curr != lastProgress) {
            lastProgress = curr
            iConfig.iNoticeTitle = "$iName - 正在下载"
            iConfig.iNoticeContent = "当前下载进度 $lastProgress %"
            iNoticeUtil._SetConfig(iConfig).iNoticeWithProgressShow(this, iUpdateManager._Instance().iNoticeIcon, max, progress)
        }
    }

    /**
     * 下载完成
     */
    override fun done(apk: File) {
        iConfig.iNoticeTitle = "$iName - 下载完成"
        iConfig.iNoticeContent = "点击进行安装"
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
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        iNoticeUtil._SetConfig(iConfig).iNoticeWithIntentShow(this, iUpdateManager._Instance().iNoticeIcon, pi)
        this.startActivity(intent)
    }

    /**
     * 下载出错
     */
    override fun error(e: Exception) {
        iConfig.iNoticeTitle = "$iName - 下载出错"
        iConfig.iNoticeContent = "点击重新下载"
        val intent = Intent(this, iUpdateService::class.java)
        val pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        iNoticeUtil._SetConfig(iConfig).iNoticeWithIntentShow(this, iUpdateManager._Instance().iNoticeIcon, pi)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}