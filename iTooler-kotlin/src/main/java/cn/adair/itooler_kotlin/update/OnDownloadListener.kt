package cn.adair.itooler_kotlin.update

import java.io.File

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/20/020.
 * slight negligence may lead to great disaster~
 */
interface OnDownloadListener {

    /**
     * 开始下载
     */
    abstract fun start()

    /**
     * 下载中
     * @param max      总进度
     * @param progress 当前进度
     */
    abstract fun downloading(max: Int, progress: Int)

    /**
     * 下载完成
     * @param apk 下载好的apk
     */
    abstract fun done(apk: File)

    /**
     * 下载出错
     * @param e 错误信息
     */
    abstract fun error(e: Exception)


}