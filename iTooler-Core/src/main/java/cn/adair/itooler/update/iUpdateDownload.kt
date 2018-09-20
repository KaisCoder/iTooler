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
 * cn.adair.itooler.update
 * Created by Administrator on 2018/9/18/018.
 * slight negligence may lead to great disaster~
 */
class iUpdateDownload(iCtx: Context, iPath: String) {

    var iPath: String
    var iContext: Context

    init {
        this.iPath = iPath
        this.iContext = iCtx
    }

    lateinit var iUri: String
    lateinit var iName: String
    lateinit var iListener: OnUpdateListener

    fun download(iUri: String, iName: String, iListener: OnUpdateListener) {
        this.iUri = iUri
        this.iName = iName
        this.iListener = iListener
        val executor = ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, LinkedBlockingQueue<Runnable>(), ThreadFactory { r ->
            val thread = Thread(r)
            thread.name = "app update thread"
            thread
        })
        executor.execute(runnable)
    }

    var runnable = Runnable {
        _FullDownload()
    }

    fun _FullDownload() {
        iListener.start()
        try {
            val url = URL(iUri)
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
                val file = File(iPath, iName)
                val outputStream = FileOutputStream(file)
                var len = 0
                while ((inputStream.read(buffer).apply { len = this }) != -1) {
                    //将获取到的流写入文件中
                    outputStream.write(buffer, 0, len)
                    progress += len
                    iListener.downloading(length, progress)
                }
                //完成io操作,释放资源
                outputStream.flush()
                outputStream.close()
                inputStream.close()
                iListener.done(file)
            } else {
                iListener.error(SocketTimeoutException("连接超时！"))
            }
            con.disconnect()
        } catch (e: Exception) {
            iListener.error(e)
            e.printStackTrace()
        }
    }

}

