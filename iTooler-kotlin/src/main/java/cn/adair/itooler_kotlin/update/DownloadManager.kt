package cn.adair.itooler_kotlin.update

import android.annotation.SuppressLint
import android.content.Context

/**
 * cn.adair.itooler_kotlin.update
 * Created by Administrator on 2018/7/23/023.
 * slight negligence may lead to great disaster~
 */
class DownloadManager private constructor() {

    lateinit var apkUri:String

    lateinit var apkName:String

    lateinit var loadPath:String

    companion object {

        lateinit var context: Context

        private var INSTANCE: DownloadManager? = null

        @Synchronized
        fun getInstance(context: Context): DownloadManager {
            if (INSTANCE == null) {
                INSTANCE = DownloadManager()
            }
            DownloadManager.context = context
            return INSTANCE!!
        }
    }





}