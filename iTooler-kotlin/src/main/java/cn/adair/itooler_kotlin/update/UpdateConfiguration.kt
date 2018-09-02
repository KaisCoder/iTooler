package cn.adair.itooler_kotlin.update

import android.app.NotificationChannel

class UpdateConfiguration {
    /**
     * 通知栏id
     */
    private var notifyId = 1011
    /**
     * 适配Android O的渠道通知
     */
    private var notificationChannel: NotificationChannel? = null
    /**
     * 用户自定义的下载管理
     */
    private var httpManager: BaseHttpDownloadManager? = null
    /**
     * 是否需要支持断点下载 (默认是支持的)
     */
    private var breakpointDownload = true
    /**
     * 是否需要日志输出（错误日志）
     */
    private var enableLog = true
    /**
     * 是否需要显示通知栏进度
     */
    private var showNotification = true
    /**
     * 下载过程回调
     */
    private var onDownloadListener: OnDownloadListener? = null
    /**
     * 下载完成是否自动弹出安装页面 (默认为true)
     */
    private var jumpInstallPage = true
    /**
     * 是否强制升级(默认为false)
     */
    private var forcedUpgrade = false


    fun getNotifyId(): Int {
        return notifyId
    }

    fun setNotifyId(notifyId: Int): UpdateConfiguration {
        this.notifyId = notifyId
        return this
    }

    fun setHttpManager(httpManager: BaseHttpDownloadManager): UpdateConfiguration {
        this.httpManager = httpManager
        return this
    }

    fun getHttpManager(): BaseHttpDownloadManager? {
        return httpManager
    }

    fun isBreakpointDownload(): Boolean {
        return breakpointDownload
    }

    fun setBreakpointDownload(breakpointDownload: Boolean): UpdateConfiguration {
        this.breakpointDownload = breakpointDownload
        return this
    }

    fun isEnableLog(): Boolean {
        return enableLog
    }

    fun setEnableLog(enableLog: Boolean): UpdateConfiguration {
        this.enableLog = enableLog
        return this
    }

    fun getOnDownloadListener(): OnDownloadListener? {
        return onDownloadListener
    }

    fun setOnDownloadListener(onDownloadListener: OnDownloadListener): UpdateConfiguration {
        this.onDownloadListener = onDownloadListener
        return this
    }

    fun isJumpInstallPage(): Boolean {
        return jumpInstallPage
    }

    fun setJumpInstallPage(jumpInstallPage: Boolean): UpdateConfiguration {
        this.jumpInstallPage = jumpInstallPage
        return this
    }

    fun getNotificationChannel(): NotificationChannel? {
        return notificationChannel
    }

    fun isShowNotification(): Boolean {
        return showNotification
    }

    fun setShowNotification(showNotification: Boolean): UpdateConfiguration {
        this.showNotification = showNotification
        return this
    }

    fun setNotificationChannel(notificationChannel: NotificationChannel): UpdateConfiguration {
        this.notificationChannel = notificationChannel
        return this
    }

    fun isForcedUpgrade(): Boolean {
        return forcedUpgrade
    }

    fun setForcedUpgrade(forcedUpgrade: Boolean): UpdateConfiguration {
        this.forcedUpgrade = forcedUpgrade
        return this
    }
}