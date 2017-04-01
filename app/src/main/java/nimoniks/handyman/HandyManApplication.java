package nimoniks.handyman;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ighotouch on 01/04/2017.
 */

public class HandyManApplication extends Application {

    Retrofit retrofit;
    private static HandyManApplication sInstance;
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
    public static HandyManApplication getInstance() {
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
