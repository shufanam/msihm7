package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.SessionUtil;

public class ContinueActivity extends Activity {

    public static Activity SPLASH_ACTIVITY;
    protected boolean _active = true;
    protected int _splashTime = 2200;
    TextView tv_splash_version;
    String version;
    Handler handler;
    private SessionUtil sessionUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_continue);

        TextView textView = (TextView) findViewById(R.id.tv_continue);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard();
            }
        });
    }

    void dashboard() {
        finish();
        Intent k = new Intent(getApplicationContext(),
                DashBoardActivity.class);
        startActivity(k);
    }


}