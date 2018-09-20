package cn.adair.itooler.tooler

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.util.*

/**
 * cn.adair.itooler_kotlin.tool
 * Created by Administrator on 2018/6/11/011.
 * slight negligence may lead to great disaster~
 */
class iUuider {

    private var PREFS_FILE: String = "device_id.xml"
    private var PREFS_DEVICE_ID: String = "device_id"

    @SuppressLint("HardwareIds")
    constructor(context: Context) {
        synchronized(iUuider::class.java) {
            val mSP: SharedPreferences = context.getSharedPreferences(PREFS_FILE, 0)
            val mId: String? = mSP.getString(PREFS_DEVICE_ID, null)
            if (!TextUtils.isEmpty(mId)) {
                iUuid = UUID.fromString(mId)
            } else {
                val androidId = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
                if (!"9774d56d682e549c".equals(androidId)) {
                    iUuid = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
                } else {
                    val TelephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    @SuppressLint("MissingPermission")
                    @SuppressWarnings("deprecation")
                    val deviceId = TelephonyManager.deviceId
                    iUuid = if (deviceId != null) UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("utf8"))) else UUID.randomUUID()
                }
                mSP.edit().putString(PREFS_DEVICE_ID, iUuid.toString()).apply()
            }
        }
    }

    companion object {
        private lateinit var iUuid: UUID
        fun deviceUuid(): UUID = iUuid
    }

}