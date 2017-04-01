package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.HeadersUtil;

public class GPSDialogActivity extends Activity {

    public static Activity GPSDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HeadersUtil.removeNotifBar(this);

        GPSDialog = this;
        setContentView(R.layout.gps);
        Button gps_sett_button = (Button) findViewById(R.id.gps_sett_button);
        gps_sett_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                finish();
            }
        });
        TextView gps_cancel_button = (TextView) findViewById(R.id.gps_cancel_button);
        gps_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
