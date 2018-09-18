package cn.adair.itooler.update

import java.io.File

/**
 * 更新下载监听
 * cn.adair.itooler.update
 * Created by Administrator on 2018/9/18/018.
 * slight negligence may lead to great disaster~
 */
interface OnUpdateListener {

    /**
     * 开始下载
     */
    fun start()

    /**
     * 下载中
     * @param max      总进度
     * @param progress 当前进度
     */
    fun downloading(max: Int, progress: Int)

    /**
     * 下载完成
     * @param apk 下载好的apk
     */
    fun done(apk: File)

    /**
     * 下载出错
     * @param e 错误信息
     */
    fun error(e: Exception)

}