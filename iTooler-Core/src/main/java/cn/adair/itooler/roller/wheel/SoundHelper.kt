package cn.adair.itooler.roller.wheel

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.annotation.FloatRange
import android.support.annotation.RawRes

/**
 * 音频辅助类
 * cn.adair.itooler.roller.wheel
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
class SoundHelper() {

    var mSoundPool: SoundPool? = null
    var mSoundId: Int = 0
    var mPlayVolume: Float = 0.0f

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = SoundPool.Builder().build()
        } else {
            @Suppress("DEPRECATION")
            mSoundPool = SoundPool(1, AudioManager.STREAM_SYSTEM, 1)
        }
    }

    /**
     * 初始化 SoundHelper
     * @return SoundHelper 对象
     */
    companion object {

        fun obtain(): SoundHelper {
            return SoundHelper()
        }

    }

    /**
     * 加载音频资源
     * @param context 上下文
     * @param resId   音频资源 [RawRes]
     */
    fun load(context: Context, @RawRes resId: Int) {
        if (mSoundPool != null) {
            mSoundId = mSoundPool!!.load(context, resId, 1)
        }
    }

    /**
     * 设置音量
     * @param playVolume 音频播放音量 range 0.0-1.0
     */
    fun setPlayVolume(@FloatRange(from = 0.0, to = 1.0) playVolume: Float) {
        this.mPlayVolume = playVolume
    }

    /**
     * 获取音量
     * @return 音频播放音量 range 0.0-1.0
     */
    fun getPlayVolume(): Float {
        return mPlayVolume
    }

    /**
     * 播放声音效果
     */
    fun playSoundEffect() {
        if (mSoundPool != null && mSoundId != 0) {
            mSoundPool!!.play(mSoundId, mPlayVolume, mPlayVolume, 1, 0, 1f)
        }
    }

    /**
     * 释放SoundPool
     */
    fun release() {
        if (mSoundPool != null) {
            mSoundPool!!.release()
            mSoundPool = null
        }
    }

}