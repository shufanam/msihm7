package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.SessionUtil;

public class SignInActivity extends Activity {

    public static Activity SIGNIN_ACTIVITY;
    private SessionUtil sessionUtil;
    String strUsername, strPassword;
    Handler handler;
    Vibrator vibrator;
    public AppCompatEditText emailPhoneEditText, passwordEditText;
    TextView tv_fgtPswd, tv_prompt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.fragment_signin);

        SIGNIN_ACTIVITY = this;

        sessionUtil = SessionUtil.getInstance();

        handler = new Handler();
        initView();
        hideKeyBoard();
    }

    void hideKeyBoard() {
        SIGNIN_ACTIVITY.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initView() {

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//
        // converting xml to java
        emailPhoneEditText = (AppCompatEditText) findViewById(R.id.emailPhoneEditText);
        passwordEditText = (AppCompatEditText) findViewById(R.id.passwordEditText);

        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // extracting string from java txt box
                strUsername = emailPhoneEditText.getText().toString().toLowerCase();
                strPassword = passwordEditText.getText().toString();

                if (strUsername.equals("")) {
                    try {
                        Toast.makeText(SignInActivity.this, "Missing Email/Phone No.", Toast.LENGTH_SHORT).show();//makeToast("Please enter ID");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(500);
                    return;
                }

                if (strPassword.equals("")) {
                    try {
                        Toast.makeText(SignInActivity.this, "Missing Password", Toast.LENGTH_SHORT).show();//("Please enter Password");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Vibrate for 500 milliseconds
                    vibrator.vibrate(500);
                    return;
                }

                disableViews();
                login(strUsername, strPassword);
            }
        });

        TextView register = (TextView) findViewById(R.id.register);
        register.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

        TextView skip = (TextView) findViewById(R.id.skip);
        skip.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startDashboard();
            }
        });
    }

    private void login(String strUsername, String strPassword) {
        finish();
        startDashboard();
    }

    private void disableViews() {

    }


    private void startSignUp() {
        Intent k = new Intent(getApplicationContext(),
                RegisterActivity.class);
        startActivity(k);
    }

    void startDashboard() {
//        finish();
        Intent k = new Intent(getApplicationContext(),
                DashBoardActivity.class);
        startActivity(k);
    }

}