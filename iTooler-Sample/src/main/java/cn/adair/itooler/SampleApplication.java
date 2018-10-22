package cn.adair.itooler;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.adair.itooler.tooler.iLogger;
import cn.adair.itooler.tooler.iSPer;

/**
 * cn.adair.itooler_sample
 * Created by Administrator on 2018/6/11/011.
 * slight negligence may lead to great disaster~
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        iTooler.INSTANCE.init(this, true).initOther("iTooler");
        iSPer.INSTANCE._Put("TEST", "test");
        iSPer.INSTANCE._Put("DEMO", "demo");
        iSPer.INSTANCE._Exist("TEST");
        iLogger.INSTANCE.e("----->" + iSPer.INSTANCE._Exist("TEST"));
        iLogger.INSTANCE.e("----->" + iSPer.INSTANCE._Exist("Demo"));
        iLogger.INSTANCE.e("----->" + iSPer.INSTANCE._GetAll().toString());
        iSPer.INSTANCE._Clear();
        iLogger.INSTANCE.e("----->" + iSPer.INSTANCE._GetAll().toString());
    }

    /**
     * 解决低版本 Didn't find class "android.support.v4.content.FileProvider"
     * 貌似他能解决很多问题
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this); //解决app总方法数超过65535 问题
    }

}
