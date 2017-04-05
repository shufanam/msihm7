package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.SessionUtil;

public class SplashActivity extends FragmentActivity {

    public static Activity SPLASH_ACTIVITY;
    protected boolean _active = true;
    protected int _splashTime = 2200;
    String version;
    Handler handler;
    private SessionUtil sessionUtil;
    //    WebService webservice;
    TextView tv_continue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_splash);

        SPLASH_ACTIVITY = this;

        sessionUtil = SessionUtil.getInstance();

        handler = new Handler();

        version = getSoftwareVersion();
//        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);

//        tv_continue = (TextView) findViewById(R.id.tv_continue);
//        tv_continue.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//        tv_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dashboard();
//            }
//        });

        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }

//                    Thread.sleep(1100);
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            tv_continue.setVisibility(View.VISIBLE);
//                        }
//                    });

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    // START SERVER
//                    DB.startServer();

                    sessionUtil.setAppVersion(version);
                    dashboard();
                }
            }
        };

        splashTread.start();
    }

    void dashboard() {
        finish();
        Intent k = new Intent(getApplicationContext(),
                DashBoardActivity.class);
        startActivity(k);
    }


    public String getSoftwareVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),
                    0);

        } catch (PackageManager.NameNotFoundException e) {
//			Log.e("TAG", "Package Manager name not found", e);
        }
        return packageInfo.versionName;
    }
}