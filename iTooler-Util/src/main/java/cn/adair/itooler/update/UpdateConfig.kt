package cn.adair.itooler.update

import android.app.NotificationChannel

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/24/024.
 * slight negligence may lead to great disaster~
 */
class UpdateConfig {

    /**
     * 通知栏id
     */
    var notifyId = 1001
    /**
     * 适配Android O的渠道通知
     */
    lateinit var notificationChannel: NotificationChannel
    /**
     * 用户自定义的下载管理
     */
    lateinit var downloadManager: BaseHttpDownloadManager
    /**
     * 是否需要支持断点下载 (默认是支持的)
     */
    var breakpointDownload = true
    /**
     * 是否需要日志输出（错误日志）
     */
    var enableLog = true
    /**
     * 是否需要显示通知栏进度
     */
    var showNotification = true
    /**
     * 下载过程回调
     */
    lateinit var onDownloadListener:OnDownloadListener
    /**
     * 下载完成是否自动弹出安装页面 (默认为true)
     */
    var jumpInstallPage = true
    /**
     * 是否强制升级(默认为false)
     */
    var forcedUpgrade = true

}