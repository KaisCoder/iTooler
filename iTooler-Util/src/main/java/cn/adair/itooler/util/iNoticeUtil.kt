package cn.adair.itooler.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import cn.adair.itooler.tool.iLogger

/**
 * 通知
 * cn.adair.itooler.util
 * Created by Administrator on 2018/9/14/014.
 * slight negligence may lead to great disaster~
 */
object iNoticeUtil {

    // 通知id
    var iNoticeId: Int = 1001;
    //  通知分类
    var iChannelId: String = "default";
    var iChannelName: String = "默认名称";


    /**
     * 构建一个消息通知
     * @icon 图标
     * @title 消息标题
     * @content 消息内容
     */
    private fun iNoticeBuilder(context: Context, icon: Int, title: String, content: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, iChannelId)
                .setSmallIcon(icon).setContentTitle(title)
                .setContentText(content).setAutoCancel(true)  // 点击是否可取消
                .setPriority(NotificationCompat.PRIORITY_MAX) // 设置优先级
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .setOngoing(true)
    }

    /**
     * 展示通知
     */
    fun iNoticeShow(context: Context, icon: Int, title: String, content: String) {
        var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        }
        val builder = iNoticeBuilder(context, icon, title, content)
        manager.notify(iNoticeId, builder.build())
    }


    /**
     * 展示通知
     */
    fun iNoticeShow(context: Context, icon: Int, title: String, content: String, intent: PendingIntent) {
        var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            iLogger.e("---->" + Build.VERSION.SDK_INT)
            afterO(manager)
        }
        val builder = iNoticeBuilder(context, icon, title, content)
        builder.setContentIntent(intent)
        manager.notify(iNoticeId, builder.build())
    }

    /**
     * 版本号在26之后的处理
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun afterO(manager: NotificationManager) {
        iLogger.e("---------->")
        var channel = NotificationChannel(iChannelId, iChannelName, NotificationManager.IMPORTANCE_LOW);
        channel.enableLights(true)
        channel.setShowBadge(true)
        manager.createNotificationChannel(channel)
    }

}