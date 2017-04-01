package nimoniks.handyman.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import nimoniks.handyman.HandyManApplication;
import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.SessionUtil;
import nimoniks.handyman.webservice.HandyManService;
import nimoniks.handyman.webservice.webServiceResponseObjects.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends Activity {

    public static Activity REGISTER_ACTIVITY;
    Handler handler;
    Retrofit retrofit;
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

        makeRegistrationRequest();
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

    public void makeRegistrationRequest(){
        retrofit = HandyManApplication.getInstance().getRetrofit();
        HandyManService service =  retrofit.create(HandyManService.class);

        Call<RegistrationResponse> registrationResponseCall = service.postRegistration("firstname", "lastname", "09043333333", "lagos", "asdff@dfg.f", "wwwwww", "wwwwww");
        registrationResponseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                String responses = response.body().toString();
                String responsess = response.message();

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                String error = t.getMessage();

            }
        });
    }
}