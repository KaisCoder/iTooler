package cn.adair.itooler.notice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.util.Log

/**
 * 通知
 * cn.adair.itooler.util
 * Created by Administrator on 2018/9/14/014.
 * slight negligence may lead to great disaster~
 */
object iNoticeUtil {

    var TAG = "iNotice"

    lateinit var iConfig: iNoticeConfig

    fun _SetConfig(noticeConfig: iNoticeConfig): iNoticeUtil {
        this.iConfig = noticeConfig
        return this
    }

    /**
     * 构建一个消息通知
     * @icon 图标
     * @title 消息标题
     * @content 消息内容
     */
    private fun iNoticeBuilder(context: Context, icon: Int, title: String, content: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, iConfig.iChannelId)
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
        Log.d(TAG, "---->当前使用API版本号：" + Build.VERSION.SDK_INT)
        var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        }
        val builder = iNoticeBuilder(context, icon, title, content)
        manager.notify(iConfig.iNoticeId, builder.build())
    }

    /**
     * 展示通知
     *
     */
    fun iNoticeWithIntentShow(context: Context, icon: Int, intent: PendingIntent) {
        var manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d(TAG, "---->当前使用API版本号：" + Build.VERSION.SDK_INT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            afterO(manager)
        }
        val builder = iNoticeBuilder(context, icon, iConfig.iNoticeTitle, iConfig.iNoticeContent)
        builder.setContentIntent(intent)
        manager.notify(iConfig.iNoticeId, builder.build())
    }

    /**
     * 版本号在26之后的处理
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun afterO(manager: NotificationManager) {
        var channel = NotificationChannel(iConfig.iChannelId, iConfig.iChannelName, NotificationManager.IMPORTANCE_LOW);
        channel.enableLights(true)
        channel.setShowBadge(true)
        manager.createNotificationChannel(channel)
    }

}