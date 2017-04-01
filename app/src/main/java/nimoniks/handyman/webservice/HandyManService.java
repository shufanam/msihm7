package nimoniks.handyman.webservice;

import nimoniks.handyman.webservice.webServiceResponseObjects.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ighotouch on 01/04/2017.
 */

public interface HandyManService {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjUyMDIwZmM2MTcxYzU3ZDA2MTAxNTg4YTJmZDAwNzdhYWVkNTRmZGI3YjQzNTkzYTc0NTE0ODMwNGMxYjA3MTAxNGNjYmI1ZGRkMWRjZDA4In0.eyJhdWQiOiIxIiwianRpIjoiNTIwMjBmYzYxNzFjNTdkMDYxMDE1ODhhMmZkMDA3N2FhZWQ1NGZkYjdiNDM1OTNhNzQ1MTQ4MzA0YzFiMDcxMDE0Y2NiYjVkZGQxZGNkMDgiLCJpYXQiOjE0OTEwNDg5MTQsIm5iZiI6MTQ5MTA0ODkxNCwiZXhwIjoxODA2NTgxNzEzLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.lU69XK7Vvhi8eF3xCrpuL1MCbdjjR5TZMVMJS1ZjGEU6EEqIGQI9utbAQXcU5JlfFcUTZjFOrAg2ahwFyyg6lxn6lMRQYwVRhHwfoCQXe01XvtuEg4Zl0gwbDdBGrxv-bLJ__uwMwSVFVsbxdjMXXZfJrqEibiDJePCpDlFaUswVbSjjC6tGSucIPMgpwIz_fC_jWz1AZgcopGFyAdfTPKV7aO7V9IuTzJpKg5aATbvZdddtmBPtFpeDK4w4VhNnhPlRhcnm7GtcYZ-O2dqxRSfkAW-K1fiZZ4mO5x9p9-rcYlKUWAS4q_y7hLYSBMoeb3xJUk3p5_z_NfsTfedZXknIU7b6Fr1nRdOOa7t6VP6VRUTT99HAW7A9WAGJBC15Aj1RxsBuY4fT4jX5RGHPbTRkc5ntzzXQRN4qy2uDB_8v8Dk2pkh-DQJJwdHSUQl2CA0PmMvKRAJ_XmIX2gTkeVHE9xYvKd7cnAhWFQcQFDREO__yWFYhuR2eMdDE2I-G6T6_7fvSYhAZPlIvhpCqZcYLUg4LA9fhD9Aja_uzthbli5l_dtVjub_w6X3uIxdjQm2H9MFXywWxyOQl2tcJIAOBmVN-TZGwoeQavu1Z2Hs_GXaWIErTNgU-XdUnW7ujFfsBqwDyPAfil8GBnEEAik9klgsgWybuIbCUG9OqmOg"
    })
    @POST("api/artisan-register")
    Call<RegistrationResponse> postRegistration(
            @Path("first_name") String firstName,
            @Path("last_name") String lastName,
            @Path("phone") String phone,
            @Path("state") String state,
            @Path("email") String email,
            @Path("password") String password,
            @Path("password_confirmation") String passwordConfrim
            );
}
