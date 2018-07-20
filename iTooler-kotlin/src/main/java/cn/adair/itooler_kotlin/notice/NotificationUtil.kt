package cn.adair.itooler_kotlin.notice

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

/**
 * cn.adair.itooler_kotlin.notice
 * Created by Administrator on 2018/7/20/020.
 * slight negligence may lead to great disaster~
 */
object NotificationUtil {

    /**
     * 构建一个消息
     */
    private fun builderNotice(context: Context, icon: Int, title: String, content: String): NotificationCompat.Builder {
        var channelId = ""
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelId = getNoticeChannelId()
        }
        return NotificationCompat.Builder(context, channelId).setSmallIcon(icon).setAutoCancel(false).setContentTitle(title)
                .setWhen(System.currentTimeMillis()).setContentText(content).setOngoing(true)
    }

    /**
     * 获取通知渠道id
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNoticeChannelId(): String {
        var channel: NotificationChannel =
        return ""
    }


}