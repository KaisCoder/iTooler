package cn.adair.itooler_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.adair.itooler_kotlin.statusbar.iStatusBar;
import cn.adair.itooler_kotlin.tool.iLogger;
import cn.adair.itooler_kotlin.tool.iToaster;
import cn.adair.itooler_kotlin.tool.iUuider;


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

            }
        });

    }
}
