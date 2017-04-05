package nimoniks.handyman.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import nimoniks.handyman.HandyManWebServices;
import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.Base64;
import nimoniks.handyman.utilities.KeyboardUtil;
import nimoniks.handyman.utilities.LoggerUtil;
import nimoniks.handyman.utilities.StringManagerUtil;
import nimoniks.handyman.webservice.HandyManService;
import retrofit2.Retrofit;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

import static android.view.View.inflate;

public class DashBoardActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    Button search;
    Button favorites;
    Button profile;
    Button about;
    LinearLayout ll_map, ll_favorites, ll_about, ll_profile, ll_search_bg, ll_dashboard_menu, ll_splash;
    LinearLayout ll_skills_list;
    CircleImageView iv_user_image;
    private byte[] byteArray;

    public static Activity DASHBOARD;

    private TextView spaceView;
    private TextView tv_edit_details;
    Retrofit retrofit;
    HandyManService service;
    TextInputLayout findHMWrapper;

    MultiAutoCompleteTextView findHMEditText;
    ImageView tv_dropdown_handyman, iv_menu, iv_menu1, iv_menu2;

    LinearLayout fragment_profile_details;
    View fragment_signin, fragment_register;
    View profile_item;
    View profileDetailsView = null;
    ScrollView sv_signin;
    EditText emailPhoneEditText;
    Button signup_button;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_dashboard);
        DASHBOARD = this;
        retrofit = HandyManWebServices.getInstance().getRetrofit();
        service = retrofit.create(HandyManService.class);
        handler = new Handler();

        initDashBoard();
    }

    //        handler.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
    private void initDashBoard() {

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                initSplash();
                initSearchFragment();
                initFavoriteFragment();
                initProfileFragment();
                initAboutFragment();
                initMenuBar();

                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        search();
                    }
                });
            }
        }).start();
    }

    private void initSplash() {
        ll_splash = (LinearLayout) findViewById(R.id.ll_splash);

        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                ll_splash.setVisibility(View.GONE);
            }
        });
    }

    private void initAboutFragment() {
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        TextView aboutVersion = (TextView) findViewById(R.id.about_version);
        aboutVersion.setText(aboutVersion.getText() + getSoftwareVersion());
    }

    private void initProfileFragment() {
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);

        sv_signin = (ScrollView) findViewById(R.id.sv_signin);
        emailPhoneEditText = (EditText) findViewById(R.id.emailPhoneEditText);
        emailPhoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    KeyboardUtil.hideKeyboard(DASHBOARD);
//                    sv_signin.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });

        iv_user_image = (CircleImageView) findViewById(R.id.user_image);
        iv_user_image.setOnClickListener(onClick);

        fragment_profile_details = (LinearLayout) findViewById(R.id.fragment_profile_details);
        fragment_signin = (View) findViewById(R.id.fragment_signin);
        fragment_register = (View) findViewById(R.id.fragment_register);

        signup_button = (Button) findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandyManWebServices.makeRegistrationRequest();
            }
        });

        TextView register = (TextView) findViewById(R.id.register_link);
        register.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignup();
            }
        });

        TextView exit_registration = (TextView) findViewById(R.id.exit_registration);
        exit_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignin();
            }
        });

        tv_edit_details = (TextView) findViewById(R.id.tv_edit_details);
        tv_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_skills_list = (LinearLayout) findViewById(R.id.ll_skills_list);

        initProfileSkills();

        ImagePicker.setMinQuality(250, 250);
    }

    private void initFavoriteFragment() {
        ll_favorites = (LinearLayout) findViewById(R.id.ll_favorite);
    }

    ArrayAdapter<String> adapter;

    private void initSearchFragment() {

        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        findHMWrapper = (TextInputLayout) findViewById(R.id.findHMWrapper);
        tv_dropdown_handyman = (ImageView) findViewById(R.id.tv_dropdown_handyman);
        tv_dropdown_handyman.setOnClickListener(onClick);
        ll_search_bg = (LinearLayout) findViewById(R.id.ll_search_bg);
        ll_dashboard_menu = (LinearLayout) findViewById(R.id.ll_dashboard_menu);

        handler.post(new Runnable() {
            @Override
            public void run() {
                ll_search_bg.setVisibility(View.VISIBLE);
                ll_dashboard_menu.setVisibility(View.VISIBLE);
            }
        });

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, StringManagerUtil.handymen);
        findHMEditText = (MultiAutoCompleteTextView) findViewById(R.id.findHMEditText);
        findHMEditText.setFocusableInTouchMode(false);
        handler.post(new Runnable() {
            @Override
            public void run() {
                findHMEditText.setAdapter(adapter);
                menu_more();
            }
        });

        findHMEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        findHMEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String string = findHMEditText.getText().toString();

                if (string.length() == 0 || string.equalsIgnoreCase(""))
                    return;

                sanitizeText(string);
                KeyboardUtil.hideKeyboard(DASHBOARD);
                findHMEditText.setFocusableInTouchMode(false);

                getMyLocation();
            }
        });

        findHMEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

//                String s = searchEditText.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

//                    if (StringUtils.isNotBlank(s.toString())) {
//                        selected = ListOption.Refined;
//                        searchList(s.toString());
//                    } else {
//                        selected = ListOption.Unsearched;
//                        setupList(faqList);
//                    }
                    KeyboardUtil.hideKeyboard(DASHBOARD);
                }
                return true;
            }
        });

        findHMEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findHMEditText.setFocusableInTouchMode(true);
                findHMEditText.setText("");
            }
        });

    }

    private void initMenuBar() {
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(onClick);

        favorites = (Button) findViewById(R.id.favorites);
        favorites.setOnClickListener(onClick);

        profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(onClick);

        about = (Button) findViewById(R.id.about);
        about.setOnClickListener(onClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

        if (bitmap != null) {
//            processCameraImage(bitmap);
            iv_user_image.setImageBitmap(bitmap);
        }
    }

    private void processCameraImage(Bitmap photo) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();

        String image = Base64.encodeBytes(byteArray);
    }

    public void onPickImage(View view) {
        // Click on image button
        ImagePicker.pickImage(this, "Select your image:");
    }

    void launchSignin() {
        fragment_profile_details.setVisibility(View.GONE);
        fragment_signin.setVisibility(View.VISIBLE);
        fragment_register.setVisibility(View.GONE);
    }

    void launchSignup() {
        fragment_profile_details.setVisibility(View.GONE);
        fragment_signin.setVisibility(View.GONE);
        fragment_register.setVisibility(View.VISIBLE);
    }

    void initProfileSkills() {
        Drawable icon = getResources().getDrawable(R.drawable.electrician);
        Drawable icon2 = getResources().getDrawable(R.drawable.plumber);

        ll_skills_list.removeAllViews();
        try {
            for (int i = 0; i < 2; i++) {

                profile_item = inflate(getApplicationContext(), R.layout.fragment_profile_selector, null);

//                TextView tv_carrier_name = (TextView) profile_item.findViewById(R.id.tv_carrier_name);
//                TextView tv_carrier_cost = (TextView) profile_item.findViewById(R.id.tv_carrier_cost);
//                ImageView iv_route_icon = (ImageView) port_folio.findViewById(R.id.iv_route_icon);

//                if ((i % 2) == 0) {
//                    tv_carrier_name.setText("Etihad Airways");
//                    tv_carrier_cost.setText("N 100, 000");
//                    Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
//                    iv_route_icon.setImageBitmap(bitmap);
//
//                } else {
//                    tv_carrier_name.setText("Virgin Atlantic Airways");
//                    tv_carrier_cost.setText("N 105, 000");
//                    Bitmap bitmap = ((BitmapDrawable) icon2).getBitmap();
//                    iv_route_icon.setImageBitmap(bitmap);
//                }

//                TextView btn_details = (TextView) profile_item.findViewById(R.id.btn_details);
//                LinearLayout ll_details = (LinearLayout) profile_item.findViewById(R.id.ll_details);

                //LISTENERS & TAGS
                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                btn_details.setOnClickListener(detailsClickListener);
//                btn_details.setTag(ll_details);

//                port_folio.setOnClickListener(detailsClickListener);
//                port_folio.setTag(port_folio);

                //ADD VIEWS
                spaceView = new TextView(DASHBOARD);
                spaceView.setText("");

                //ROOT HOLDER
                // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                ll_skills_list.addView(profile_item);
                ll_skills_list.addView(spaceView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener detailsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (profileDetailsView != null) {
//            Gone
                profileDetailsView.setVisibility(View.GONE);
                profileDetailsView = null;
            } else {
//            Visible
                profileDetailsView = (View) v
                        .getTag();
                profileDetailsView.setVisibility(View.VISIBLE);
            }
        }
    };

    LinearLayout profileSelectedView = null;


    void sanitizeText(String string) {
        string = StringManagerUtil.removeComma(string);
        string = StringManagerUtil.removeLastSpace(string);

//        System.out.println("+++++++++++++ Sanitized String: " + string);

        findHMEditText.setText(string);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            clearHilite();

            switch (id) {
                case R.id.search:
                    search();
                    getMyLocation();
                    break;

                case R.id.favorites:
                    favorites();
                    break;

                case R.id.about:
                    about();
                    break;

                case R.id.profile:
                    profile();
                    break;

                case R.id.user_image:
                    onPickImage(iv_user_image);
                    break;

                case R.id.tv_dropdown_handyman:
                    menu_more();
                    break;
            }
        }
    };

    public void logOutPrompt() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                DASHBOARD);
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Do you want to exit " + getString(R.string.app_name));

        vibrator.vibrate(500);

        alertDialog.setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.cancel();
//
                        finish();
                        // END APPLICATION
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });

        alertDialog.setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    PopupMenu popupMenu;

    void menu_more() {
        popupMenu = new PopupMenu(this, findHMWrapper);
//        popupMenu = new PopupMenu(DashBoardActivity.DASHBOARD, v);
        for (String origin : StringManagerUtil.handymen) {
            MenuItem menuItem = popupMenu.getMenu().add(origin);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String handyman = item.getTitle().toString().toUpperCase();
                findHMEditText.setText(handyman);
                findHMEditText.setFocusableInTouchMode(false);
                KeyboardUtil.hideKeyboard(DASHBOARD);

                getMyLocation();

                return true;
            }
        });

        popupMenu.show();
    }

    private void getMyLocation() {
        try {
            location = getLocation();
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            position = new LatLng(latitude, longitude);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(position).title("ELECTRICIAN"));
//                .snippet("BUKOLA")
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.electrician)));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
//                    ToastUtil.makeToast(marker.getTitle(), DASHBOARD);
//                    startActivity(new Intent(DASHBOARD, RegisterActivity.class));
                    return false;
                }
            });
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            //Move the camera instantly to hamburg with a zoom of 15.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 7000, null);

        } catch (Exception e) {
            e.printStackTrace();
//            ToastUtil.makeToast("Something went wrong", DASHBOARD);
        }
    }

    private void getMyLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        position = new LatLng(latitude, longitude);

        mMap.clear();

        mMap.addMarker(new MarkerOptions().position(position).title("ELECTRICIAN"));
//                .snippet("BUKOLA")
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.electrician)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                ToastUtil.makeToast(marker.getTitle(), DASHBOARD);
//                startActivity(new Intent(DASHBOARD, RegisterActivity.class));
                return false;
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        //Move the camera instantly to hamburg with a zoom of 15.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 7000, null);
    }

    Button buffer;

    void search() {
        buffer = search;
        ll_map.setVisibility(View.VISIBLE);
        ll_favorites.setVisibility(View.GONE);
        ll_about.setVisibility(View.GONE);
        ll_profile.setVisibility(View.GONE);
        search.setBackgroundResource(R.drawable.button_yellow);
    }

    void favorites() {
        buffer = favorites;
        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.VISIBLE);
        ll_about.setVisibility(View.GONE);
        ll_profile.setVisibility(View.GONE);
        favorites.setBackgroundResource(R.drawable.button_yellow);
    }

    void about() {
        KeyboardUtil.hideKeyboard(DASHBOARD);
        buffer = about;
        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.GONE);
        ll_about.setVisibility(View.VISIBLE);
        ll_profile.setVisibility(View.GONE);
        about.setBackgroundResource(R.drawable.button_yellow);
    }

    void profile() {

        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.GONE);
        ll_about.setVisibility(View.GONE);
        ll_profile.setVisibility(View.VISIBLE);
        profile.setBackgroundResource(R.drawable.button_yellow);
    }


//    void aboutActivity() {
//        buffer = about;
//        about.setBackgroundResource(R.color.hm_yellow);
//        Intent k = new Intent(getApplicationContext(),
//                AboutActivity.class);
//        startActivity(k);
//    }


    void clearHilite() {
        search.setBackgroundResource(R.color.transparent);
        favorites.setBackgroundResource(R.color.transparent);
        profile.setBackgroundResource(R.color.transparent);
        about.setBackgroundResource(R.color.transparent);
    }


    public String getSoftwareVersion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),
                    0);

        } catch (PackageManager.NameNotFoundException e) {
//			Log.e("TAG", "Package Manager name not found", e);
        }
        return packageInfo.versionName;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    LatLng position;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

//    boolean aboutClicked = false;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        logOutPrompt();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (gps < 2) {
            try {
                // get location manager to check gps availability
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0f, this);

                boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!isGPS) {
                    gps++;

                    if (gps < 2) {
                        checkGps();
                    } else {
                        gps = 0;
//                        finish();
                    }
                } else {
                    //GPS is Available, do actions here
                    gps = 2;
                    getMyLocation();
                }

            } catch (Exception e1) {
                gps++;
                if (gps < 2) {
                    checkGps();
                } else {
                    gps = 0;
//                    finish();
                }
            }
        }
    }

    //    private void myCurrentLocation() {
//        search();
//
//        location = getLocation();
////
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//
//        if (latitude == 0.0 && longitude == 0.0) {
//            try {
//                ToastUtil.makeToast("Something went wrong", this);
//                checkGps();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // Vibrate for 500 milliseconds
//            vibrator.vibrate(500);
//            return;
//        }
//
//        ME = new LatLng(latitude, longitude);
////        LatLng ME = new LatLng(6.4358406, 3.4439455);
////        final LatLng HAMBURG = new LatLng(53.558, 9.927);
////        final LatLng KIEL = new LatLng(53.551, 9.993);
//// POSITIONS
////        Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
////                .title("Hamburg"));
//        //
////        Marker kiel = mMap.addMarker(new MarkerOptions()
////                .position(ME)
////                .title("Ekhomen Ehimen")
////                .snippet("SAP Certified Associate")
////                .icon(BitmapDescriptorFactory
////                        .fromResource(R.drawable.ekomen)));
//        //
//        // Add a marker in Sydney and move the camera
////        LatLng me = new LatLng(latitude, longitude);
//        //
//        //position = HAMBURG;
//        //position = KIEL;
//        position = ME;
//
//        mMap.addMarker(new MarkerOptions().position(position).title("NIMONIX")
//                .snippet("INVISIBILITY")
//                .icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.ekomen)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
//
//        //Move the camera instantly to hamburg with a zoom of 15.
//        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
//
//        // Zoom in, animating the camera.
////        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 10000, null);
//
//    }


    public void checkGps() {
        startActivity(new Intent(getApplicationContext(), GPSDialogActivity.class));
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) DASHBOARD.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
//                Toast.makeText(getApplicationContext(), "No GPS/Network", Toast.LENGTH_LONG).show();
                checkGps();
            } else {
//				this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    LoggerUtil.log("Network", "Network is Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        LoggerUtil.log("GPS", "GPS is Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        getMyLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        menu_more();

        try {
            GPSDialogActivity.GPSDialog.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
//    boolean canGetLocation = false;

    //	Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private int gps = 0;

    Vibrator vibrator;
    Location location;


}
