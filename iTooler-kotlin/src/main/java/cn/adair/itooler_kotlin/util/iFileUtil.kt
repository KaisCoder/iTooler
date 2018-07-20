package cn.adair.itooler_kotlin.util

import android.os.Environment
import cn.adair.itooler_kotlin.iTooler
import java.io.File

/**
 * cn.adair.itooler_kotlin.tool
 * Created by Administrator on 2018/6/22/022.
 * slight negligence may lead to great disaster~
 */
object iFileUtil {

    private val isSDCardAvailable: Boolean = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())

    /**
     * 创建一个文件夹并返回文件路径
     */
    fun isFilePath(folder: String): String {
        var sb = StringBuilder()
        if (isSDCardAvailable) {
            //---》/storage/emulated/0
            sb.append(Environment.getExternalStorageDirectory().absolutePath)
            sb.append(File.separator)
            sb.append(iTooler.TAG)
        } else {
            //---》/data/user/0/com.yidao.media/cache
            sb.append(iTooler.iCtx.getCacheDir().getAbsolutePath())
        }
        sb.append(File.separator)
        sb.append(folder)
        sb.append(File.separator)
        val file = File(sb.toString())
        if (!file.exists() || !file.isDirectory) {
            file.mkdirs()
        }
        return sb.toString()
    }

}