package cn.adair.itooler.update

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/24/024.
 * slight negligence may lead to great disaster~
 */
abstract class BaseHttpDownloadManager {

    /**
     * 下载apk
     */
    internal abstract fun download(apkUrl: String, apkName: String, listener: OnDownloadListener)

}