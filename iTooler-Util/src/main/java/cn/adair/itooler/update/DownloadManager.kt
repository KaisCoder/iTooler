package cn.adair.itooler.update

import android.content.Context
import android.content.Intent
import cn.adair.itooler.util.PermissionUtil
import cn.adair.itooler.util.iPermissionUtil

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/23/023.
 * slight negligence may lead to great disaster~
 */
class DownloadManager private constructor() {

    /**
     * 上下文
     */
    private var context: Context? = null
    /**
     * 要更新apk的下载地址
     */
    private var apkUrl = ""
    /**
     * apk下载好的名字 .apk 结尾
     */
    private var apkName = ""
    /**
     * apk 下载存放的位置
     */
    private var downloadPath: String? = null
    /**
     * 是否提示用户 "当前已是最新版本"
     * [.download]
     */
    private var showNewerToast = false
    /**
     * 通知栏的图标 资源路径
     */
    private var smallIcon = -1
    /**
     * 整个库的一些配置属性，可以从这里配置
     */
    private var configuration: UpdateConfig? = null
    /**
     * 要更新apk的versionCode
     */
    private var apkVersionCode = 1
    /**
     * 显示给用户的版本号
     */
    private var apkVersionName = ""
    /**
     * 更新描述
     */
    private var apkDescription = ""
    /**
     * 安装包大小 单位 M
     */
    private var apkSize = ""
    /**
     * 兼容Android N 添加uri权限 authorities
     */
    private var authorities = ""

    private var manager: DownloadManager? = null

    companion object {

        private var INSTANCE: DownloadManager? = null

        fun getInstance(context: Context): DownloadManager {
            if (INSTANCE == null) {
                synchronized(DownloadManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DownloadManager()
                    }
                }
            }
            INSTANCE!!.context = context
            return INSTANCE!!
        }

        /**
         * 供此依赖库自己使用.
         * @return [DownloadManager]
         * @hide
         */
        fun getInstance(): DownloadManager {
            if (INSTANCE == null) {
                throw RuntimeException("请先调用 getInstance(Context context) !")
            }
            return INSTANCE!!
        }
    }


    fun getApkUrl(): String {
        return apkUrl
    }

    fun setApkUrl(apkUrl: String): DownloadManager {
        this.apkUrl = apkUrl
        return this
    }

    fun getApkVersionCode(): Int {
        return apkVersionCode
    }

    fun setApkVersionCode(apkVersionCode: Int): DownloadManager {
        this.apkVersionCode = apkVersionCode
        return this
    }

    fun getApkName(): String {
        return apkName
    }

    fun setApkName(apkName: String): DownloadManager {
        this.apkName = apkName
        return this
    }

    fun getDownloadPath(): String? {
        return downloadPath
    }

    fun setDownloadPath(downloadPath: String): DownloadManager {
        this.downloadPath = downloadPath
        return this
    }

    fun setShowNewerToast(showNewerToast: Boolean): DownloadManager {
        this.showNewerToast = showNewerToast
        return this
    }

    fun isShowNewerToast(): Boolean {
        return showNewerToast
    }

    fun getSmallIcon(): Int {
        return smallIcon
    }

    fun setSmallIcon(smallIcon: Int): DownloadManager {
        this.smallIcon = smallIcon
        return this
    }

    fun setConfiguration(configuration: UpdateConfig): DownloadManager {
        this.configuration = configuration
        return this
    }

    fun getConfiguration(): UpdateConfig? {
        return configuration
    }

    fun getApkVersionName(): String {
        return apkVersionName
    }

    fun setApkVersionName(apkVersionName: String): DownloadManager {
        this.apkVersionName = apkVersionName
        return this
    }

    fun getApkDescription(): String {
        return apkDescription
    }

    fun setApkDescription(apkDescription: String): DownloadManager {
        this.apkDescription = apkDescription
        return this
    }

    fun getApkSize(): String {
        return apkSize
    }

    fun setApkSize(apkSize: String): DownloadManager {
        this.apkSize = apkSize
        return this
    }

    fun getAuthorities(): String {
        return authorities
    }

    fun setAuthorities(authorities: String): DownloadManager {
        this.authorities = authorities
        return this
    }

    /**
     * 开始下载
     */
    fun download() {
        if (configuration == null) {
            configuration = UpdateConfig()
        }
        if (!PermissionUtil.checkStoragePermission(context!!)) {
            //没有权限,去申请权限
            context!!.startActivity(Intent(context, PermissionActivity::class.java))
            return
        }
        context!!.startService(Intent(context, DownloadService::class.java))
    }

    /**
     * 释放资源
     */
    fun release() {
        manager = null
    }
}