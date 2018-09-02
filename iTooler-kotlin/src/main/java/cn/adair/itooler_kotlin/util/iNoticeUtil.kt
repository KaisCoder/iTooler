package cn.adair.itooler_kotlin.util

import android.content.Context
import android.support.v4.app.NotificationCompat

object iNoticeUtil {

    var iChannelId: String = "default"; //  将通知分类

    /**
     * 构建一个消息通知
     * @icon 图标
     * @title 消息标题
     * @content 消息内容
     */
    private fun iNoticeBuilder(context: Context, icon: Int, title: String, content: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, iChannelId)
                .setSmallIcon(icon).setContentTitle(title)
                .setContentText(content).setAutoCancel(false)  // 点击是否可取消
                .setPriority(NotificationCompat.PRIORITY_MAX) // 设置优先级
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
    }

    /**
     *
     */
    fun iNoticeShow(context: Context, icon: Int, title: String, content: String) {

    }


}