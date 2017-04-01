package nimoniks.handyman.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import nimoniks.handyman.smartlogin.R;
import nimoniks.handyman.utilities.Base64;
import nimoniks.handyman.utilities.KeyboardUtil;
import nimoniks.handyman.utilities.LoggerUtil;
import nimoniks.handyman.utilities.StringManagerUtil;

import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static android.view.View.inflate;

public class DashBoardActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    Button search;
    Button favorites;
    Button profile;
    //    Button portfolio;
    Button about;
    LinearLayout ll_map, ll_favorites, ll_about, ll_profile, ll_search_bg;
    LinearLayout ll_portfolio_list;
    CircleImageView user_image;
    private byte[] byteArray;

    public static Activity DASHBOARD;

    public List<String> handymen = Arrays.asList("A/C Repairer", "Artists", "Barber", "Basket weaver", "Borehole expert", "Bricklayer", "Camera man/Videographer", "Car hire service", "Car Wash", "Carpenter", "Caterer/Chef", "Cleaning Services", "Computer repairs", "Dish installer", "DJ/Sound experts", "Dry Cleaner", "Electrician", "Fumigators", "Gardeners", "Gas refills", "Generator repairers", "Hair dresser", "Makeup artist", "Mechanic", "Painter", "Panel beaters", "Phone repairer", "Printer", "Plumber", "Rental service", "Spare part dealers", "Shoemaker (Cobbler)", "Tailor", "Technician", "Vulcanizer", "Watch repairer", "Welder");
///    public static int handymenIcon[] = {R.drawable.air_condition, R.drawable.artist, R.drawable.barber, R.drawable.basket_weaver, R.drawable.bore_hole, R.drawable.brick_layer, R.drawable.camera_man, R.drawable.car_hire, R.drawable.car_wash, R.drawable.carpenter, R.drawable.caterer, R.drawable.cleaner, R.drawable.computer_repair, R.drawable.dish_installer, R.drawable.dj, R.drawable.dry_cleaner, R.drawable.electrician, R.drawable.fumigators, R.drawable.gardeners, R.drawable.gas_refill, R.drawable.generator, R.drawable.hair_dresser, R.drawable.makeup_artist, R.drawable.mechanic, R.drawable.painter, R.drawable.panel_beater, R.drawable.phone_repairer, R.drawable.printer, R.drawable.plumber, R.drawable.rental_services, R.drawable.spare_parts, R.drawable.shoe_maker, R.drawable.tailor, R.drawable.technician, R.drawable.vulcanizer, R.drawable.watch_repair, R.drawable.welder};

    //    public List<String> handymen = Arrays.asList("A/C Repairer", "Artists", "Barber", "Basket weaver", "Borehole expert", "Bricklayer", "Camera man/Videographer", "Car hire service", "Car Wash", "Carpenter", "Caterer/Chef", "Cleaning Services", "Computer repairs", "Dish installer", "DJ/Sound experts", "Dry Cleaner", "Electrician", "Fumigators");
//    public static int handymenIcon[] = {R.drawable.air_condition, R.drawable.artist, R.drawable.barber, R.drawable.basket_weaver, R.drawable.bore_hole, R.drawable.brick_layer, R.drawable.camera_man, R.drawable.car_hire, R.drawable.car_wash, R.drawable.carpenter, R.drawable.caterer, R.drawable.cleaner, R.drawable.computer_repair, R.drawable.dish_installer, R.drawable.dj, R.drawable.dry_cleaner, R.drawable.electrician, R.drawable.fumigators};
    private TextView spaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        DASHBOARD = this;

        initFragments();
        initMenuBar();
        initMap();
        search();
        initProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

        if (bitmap != null) {
//            processCameraImage(bitmap);
            user_image.setImageBitmap(bitmap);
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

    TextInputLayout findHMWrapper;

    MultiAutoCompleteTextView findHMEditText;
    ImageView tv_dropdown_handyman, iv_menu, iv_menu1, iv_menu2;

    private void initFragments() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        user_image = (CircleImageView) findViewById(R.id.user_image);
        user_image.setOnClickListener(onClick);

        ll_map = (LinearLayout) findViewById(R.id.ll_map);
        ll_favorites = (LinearLayout) findViewById(R.id.ll_favorite);
        findHMWrapper = (TextInputLayout) findViewById(R.id.findHMWrapper);
        ll_search_bg = (LinearLayout) findViewById(R.id.ll_search_bg);
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        TextView aboutVersion = (TextView) findViewById(R.id.about_version);
        aboutVersion.setText(aboutVersion.getText() + getSoftwareVersion());

        tv_dropdown_handyman = (ImageView) findViewById(R.id.tv_dropdown_handyman);
        tv_dropdown_handyman.setOnClickListener(onClick);

        //FRAGMENT LISTS
        ll_portfolio_list = (LinearLayout) findViewById(R.id.ll_portfolio_list);

        System.out.println("++++++++++++++++++++++++++++++++ Handymen: " + handymen.size());
        System.out.println("++++++++++++++++++++++++++++++++ Handymen Icons: " + handymen.size());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, handymen);
        findHMEditText = (MultiAutoCompleteTextView) findViewById(R.id.findHMEditText);
        findHMEditText.setFocusableInTouchMode(false);
        findHMEditText.setAdapter(adapter);
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

        ImagePicker.setMinQuality(250, 250);
    }

    //
    View profile_item;

    void initProfile() {
        Drawable icon = getResources().getDrawable(R.drawable.electrician);
        Drawable icon2 = getResources().getDrawable(R.drawable.plumber);

        ll_portfolio_list.removeAllViews();
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
                ll_portfolio_list.addView(profile_item);
                ll_portfolio_list.addView(spaceView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View profileDetailsView = null;

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

    void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                    onPickImage(user_image);
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
        alertDialog.setMessage("Do you want to exit HandyMan?");

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
        int i = 0;
        for (String origin : handymen) {
            MenuItem menuItem = popupMenu.getMenu().add(origin);
//            menuItem.setIcon(handymenIcon[i]);
//            i++;
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

        // +++ Force icons to show
//        Object menuHelper;
//        Class[] argTypes;
//        try {
//            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
//            fMenuHelper.setAccessible(true);
//            menuHelper = fMenuHelper.get(popupMenu);
//            argTypes = new Class[]
//                    {boolean.class};
//            menuHelper.getClass()
//                    .getDeclaredMethod("setForceShowIcon", argTypes)
//                    .invoke(menuHelper, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            popupMenu.show();
//            return;
//        }
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
                    startActivity(new Intent(DASHBOARD, RegisterActivity.class));
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
                startActivity(new Intent(DASHBOARD, RegisterActivity.class));
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
        search.setBackgroundResource(R.color.hm_yellow);
    }

    void favorites() {
        buffer = favorites;
        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.VISIBLE);
        ll_about.setVisibility(View.GONE);
        ll_profile.setVisibility(View.GONE);
        favorites.setBackgroundResource(R.color.hm_yellow);
    }

    void about() {
        buffer = about;
        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.GONE);
        ll_about.setVisibility(View.VISIBLE);
        ll_profile.setVisibility(View.GONE);
        about.setBackgroundResource(R.color.hm_yellow);
    }

    void profile() {

//        startActivity(new Intent(DASHBOARD, RegisterActivity.class));

        ll_map.setVisibility(View.GONE);
        ll_favorites.setVisibility(View.GONE);
        ll_about.setVisibility(View.GONE);
        ll_profile.setVisibility(View.VISIBLE);
        profile.setBackgroundResource(R.color.hm_yellow);
    }

    void aboutActivity() {
        buffer = about;
        about.setBackgroundResource(R.color.hm_yellow);
        Intent k = new Intent(getApplicationContext(),
                AboutActivity.class);
        startActivity(k);
    }


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

    boolean aboutClicked = false;

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
    boolean canGetLocation = false;

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
