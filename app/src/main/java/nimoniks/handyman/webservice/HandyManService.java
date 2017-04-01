package nimoniks.handyman.webservice;

import nimoniks.handyman.webservice.webServiceResponseObjects.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by ighotouch on 01/04/2017.
 */

public interface HandyManService {

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("api/artisan-register")
    Call<RegistrationResponse> listRepos(
            @Path("first_name") String firstName,
            @Path("last_name") String lastName,
            @Path("phone") String phone,
            @Path("state") String state,
            @Path("email") String email,
            @Path("password") String password
            );
}
