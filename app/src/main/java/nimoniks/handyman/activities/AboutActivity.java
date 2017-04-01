package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.HeadersUtil;

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HeadersUtil.removeNotifBar(this);
        setContentView(R.layout.about);
        TextView aboutVersion = (TextView) findViewById(R.id.about_version);
        aboutVersion.setText(aboutVersion.getText() + getSoftwareVersion());
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }

}
