package cn.adair.itooler;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.adair.itooler.notice.iNoticeConfig;
import cn.adair.itooler.notice.iNoticeUtil;
import cn.adair.itooler.permit.iPermitUtil;
import cn.adair.itooler.tooler.iFileer;
import cn.adair.itooler.tooler.iLogger;
import cn.adair.itooler.tooler.iToaster;
import cn.adair.itooler.tooler.iUuider;
import cn.adair.itooler.update.iUpdateManager;


public class MainActivity extends AppCompatActivity {

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

                iLogger.INSTANCE.e(iFileer.INSTANCE.isFilePath("images"));

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
                                iUpdateManager manager = iUpdateManager.Companion._Instance(MainActivity.this);
                                manager.setINoticeId(1001);
                                manager.setINoticeIcon(R.mipmap.ic_launcher);
                                manager.setINoticeTitle("iTooler 下载通知");
                                manager.setINoticeContent("iTooler 下载通知内容");
                                manager.setIUpdateName("YiDao.apk");
                                manager.setIUpdateUri("http://files.yidao.pro/admin/20180717/8b2649dcc2bc0ef3d7f1ecb4a1e8efae.apk");
                                manager.setIUpdatePath(iFileer.INSTANCE.isFilePath("update"));
                                manager.download();


                            }
                        }).create().show();
            }
        });

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra("init", "Notice");
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                iNoticeConfig iConfig = new iNoticeConfig();
                iConfig.setINoticeId(1001);
                iConfig.setIChannelId("notice");
                iConfig.setIChannelName("通知");
                iConfig.setINoticeTitle("通知标题");
                iConfig.setINoticeContent("通知内容");

                iNoticeUtil.INSTANCE._SetConfig(iConfig).iNoticeWithIntentShow(MainActivity.this, R.mipmap.ic_launcher, pi);

            }
        });

        findViewById(R.id.permit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPermitUtil.INSTANCE.requestPermissions(MainActivity.this, 100, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, new iPermitUtil.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        iLogger.INSTANCE.e("同意");
                    }

                    @Override
                    public void onPermissionDenied() {
                        iLogger.INSTANCE.e("拒绝");
                        iPermitUtil.INSTANCE.showTipsDialog(MainActivity.this);
                    }
                });
            }
        });

    }

}
