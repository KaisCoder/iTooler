package cn.adair.itooler_sample;

import android.app.NotificationChannel;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;

import java.io.File;

import cn.adair.itooler_kotlin.statusbar.iStatusBar;
import cn.adair.itooler_kotlin.tool.iLogger;
import cn.adair.itooler_kotlin.tool.iToaster;
import cn.adair.itooler_kotlin.tool.iUuider;
import cn.adair.itooler_kotlin.update.PermissionActivity;
import cn.adair.itooler_kotlin.util.iFileUtil;


public class MainActivity extends AppCompatActivity implements OnDownloadListener {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        iStatusBar.INSTANCE.darkMode(this);
        iStatusBar.INSTANCE.setPaddingSmart(this, mToolbar);

        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iToaster.INSTANCE.showShort("Toast Test");

                iLogger.INSTANCE.d();
                iLogger.INSTANCE.d(iUuider.Companion.deviceUuid().toString());

                iLogger.INSTANCE.i();
                iLogger.INSTANCE.i(iUuider.Companion.deviceUuid().toString());

                iLogger.INSTANCE.e();
                iLogger.INSTANCE.e(iUuider.Companion.deviceUuid().toString());

                iLogger.INSTANCE.e(iFileUtil.INSTANCE.isFilePath("images"));

            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("发现新版本")
                        .setMessage("1.支持断点下载\n2.支持Android N\n3.支持Android O\n4.支持自定义下载过程\n5.支持 设备>=Android M 动态权限的申请")
                        .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this, PermissionActivity.class));
//                                DownloadManager manager = DownloadManager.getInstance(MainActivity.this);
//                                manager.setApkName("YiDao.apk")
//                                        .setApkUrl("http://files.yidao.pro/admin/20180717/8b2649dcc2bc0ef3d7f1ecb4a1e8efae.apk")
//                                        .setDownloadPath(iFileUtil.INSTANCE.isFilePath("update"))
//                                        .setSmallIcon(R.mipmap.ic_launcher)
//                                        .download();
                            }
                        }).create().show();
            }
        });

    }

    private void startUpdate3() {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //支持断点下载
                .setBreakpointDownload(true)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置强制更新
                .setForcedUpgrade(false)
                //设置下载过程的监听
                .setOnDownloadListener(this);

        DownloadManager manager = DownloadManager.getInstance(this);
        manager.setApkName("YiDao.apk")
                .setApkUrl("http://files.yidao.pro/admin/20180717/8b2649dcc2bc0ef3d7f1ecb4a1e8efae.apk")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
                .setDownloadPath(iFileUtil.INSTANCE.isFilePath("update"))
                .setApkVersionCode(2)
                .setApkVersionName("2.1.8")
                .setApkSize("20.4")
                .setAuthorities(getPackageName())
                .setApkDescription("1.支持断点下载\n2.支持Android N\n3.支持Android O\n4.支持自定义下载过程\n5.支持 设备>=Android M 动态权限的申请\n6.支持通知栏进度条展示(或者自定义显示进度)")
                .download();
    }


    @Override
    public void start() {

    }

    @Override
    public void downloading(int max, int progress) {

    }

    @Override
    public void done(File apk) {

    }

    @Override
    public void error(Exception e) {

    }

}
