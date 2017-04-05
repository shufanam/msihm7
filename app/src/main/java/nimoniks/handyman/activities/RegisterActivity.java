package nimoniks.handyman.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.HeadersUtil;
import nimoniks.handyman.utilities.SessionUtil;
import retrofit2.Retrofit;

public class RegisterActivity extends Activity {

    public static Activity REGISTER_ACTIVITY;
    Handler handler;
    Retrofit retrofit;
    private SessionUtil sessionUtil;
    private TextView close_registration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HeadersUtil.removeNotifBar(this);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.fragment_register);

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

            }
        });

        close_registration = (TextView) findViewById(R.id.exit_registration);
        close_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }


}