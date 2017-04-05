package nimoniks.handyman;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import nimoniks.handyman.activities.DashBoardActivity;
import nimoniks.handyman.utilities.ToastUtil;
import nimoniks.handyman.webservice.HandyManService;
import nimoniks.handyman.webservice.webServiceResponseObjects.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ighotouch on 01/04/2017.
 */

public class HandyManWebServices extends Application {

    static Retrofit retrofit;
    private static HandyManWebServices sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


        retrofit = new Retrofit.Builder()
                .baseUrl("http://handym.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    // Returns the active application instance
    public static HandyManWebServices getInstance() {
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void makeRegistrationRequest() {
        retrofit = HandyManWebServices.getInstance().getRetrofit();
        HandyManService service = retrofit.create(HandyManService.class);

        Call<RegistrationResponse> registrationResponseCall = service.postRegistration("firstname", "lastname", "09043333333", "lagos", "asdfdfdff@dfg.f", "wwwwww", "wwwwww");
        registrationResponseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
//                String responses = response.body().toString();
                String responsess = response.message();
//                ToastUtil.makeLongToast("Responses: " +responses + " " + "Responses2: " +responsess, DashBoardActivity.DASHBOARD);
                ToastUtil.makeLongToast("Responses2: " +responsess, DashBoardActivity.DASHBOARD);
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                String error = t.getMessage();
                ToastUtil.makeLongToast("Error: " + error, DashBoardActivity.DASHBOARD);
            }
        });
    }
}
