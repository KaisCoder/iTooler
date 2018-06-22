package cn.adair.itooler_sample;

import android.app.Application;

import cn.adair.itooler_kotlin.iTooler;
import cn.adair.itooler_kotlin.tool.iLogger;
import cn.adair.itooler_kotlin.util.iFileUtil;

/**
 * cn.adair.itooler_sample
 * Created by Administrator on 2018/6/11/011.
 * slight negligence may lead to great disaster~
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        iTooler.INSTANCE.init(this).isDebug(true, "Sample").initOther(this);
    }
}
