package cn.adair.itooler.update

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/24/024.
 * slight negligence may lead to great disaster~
 */
class HttpDownloadManager(context: Context, apkPath: String) : BaseHttpDownloadManager() {

    var context: Context
    var apkPath: String
    lateinit var apkUrl: String
    lateinit var apkName: String
    lateinit var aLoadListener: OnDownloadListener

    init {
        this.context = context
        this.apkPath = apkPath
    }

    override fun download(apkUrl: String, apkName: String, listener: OnDownloadListener) {
        this.apkUrl = apkUrl
        this.apkName = apkName
        this.aLoadListener = listener
        var executor = ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(), object : ThreadFactory {
            override fun newThread(r: Runnable?): Thread? {
                val thread = Thread(r)
                thread.name = "app update thread"
                return thread
            }
        })
        executor.execute(runnable)

    }

    private val runnable = Runnable {
        //删除之前的安装包
        //检查是否需要断点下载
        fullDownload()
    }


    private fun fullDownload() {
        aLoadListener.start()
        try {
            val url = URL(apkUrl)
            val con = url.openConnection() as HttpURLConnection
            con.readTimeout = 5000
            con.connectTimeout = 5000
            con.setRequestProperty("Accept-Encoding", "identity")
            if (con.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = con.inputStream
                val length = con.contentLength
                //当前已下载完成的进度
                var progress = 0
                val buffer = ByteArray(1024 * 4)
                val file = File(apkPath, apkName)
                val outputStream = FileOutputStream(file)
                var len = 0
//                ((len = is.read(buffer)) != -1)
                while ((inputStream.read(buffer).apply { len = this }) != -1) {
                    //将获取到的流写入文件中
                    outputStream.write(buffer, 0, len)
                    progress += len
                    aLoadListener.downloading(length, progress)
                }
                //完成io操作,释放资源
                outputStream.flush()
                outputStream.close()
                inputStream.close()
                aLoadListener.done(file)
            } else {
                aLoadListener.error(SocketTimeoutException("连接超时！"))
            }
            con.disconnect()
        } catch (e: Exception) {
            aLoadListener.error(e)
            e.printStackTrace()
        }

    }

}