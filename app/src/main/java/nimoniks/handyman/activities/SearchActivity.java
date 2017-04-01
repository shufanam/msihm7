package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.HeadersUtil;

public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HeadersUtil.removeNotifBar(this);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }

}
