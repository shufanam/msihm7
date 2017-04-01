package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.SessionUtil;

public class RegisterActivity extends Activity {

    public static Activity REGISTER_ACTIVITY;
    Handler handler;
    private SessionUtil sessionUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_register);

        REGISTER_ACTIVITY = this;

        sessionUtil = SessionUtil.getInstance();

        handler = new Handler();
        initView();
        hideKeyBoard();
    }

    void hideKeyBoard() {
        REGISTER_ACTIVITY.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initView() {
        Button signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDashboard();
            }
        });
    }

    void startDashboard() {
//        finish();
//        Intent k = new Intent(getApplicationContext(),
//                DashBoardActivity.class);
//        startActivity(k);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}