package nimoniks.handyman.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class NetworkCheckUtil {
    static Handler handler;


    public static String location(Activity activity) {
        int mcc, mnc;
        String location = null;//"Undefined Location";
        TelephonyManager tel = (TelephonyManager)
                activity.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tel.getNetworkOperator();

//        System.out.println("+++++++++++++++ NETWORK LOCATION. . . " + networkOperator);
        if (networkOperator != null) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
            location = mcc + ":" + mnc;
//            location = "MCC/MNC: " + Integer.parseInt(networkOperator.substring(0, 3)) + "/" + Integer.parseInt(networkOperator.substring(3));
//            System.out.println(location);
        }

//		location = null;
        return location;
    }

    public static void invalidCountryCode(final Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                activity).create();
        alertDialog.setCancelable(false);
        alertDialog
                .setTitle("Network-Code Error\n");
        alertDialog
                .setMessage(":( Sorry, This application is not compatible with your SIM/Network");
        alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
        alertDialog.show();
    }

    public static boolean haveNetworkConnection(Activity act) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        // Log.i("***InneractiveAd***",
        // ">>>>>>>>>>......In haveNetworkConnection Method");

        ConnectivityManager cm = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        Log.i("", ">>>>>>>>>>......haveNetworkConnection Wifi/Mobile: "
                + haveConnectedWifi + "/" + haveConnectedMobile);
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void noConnectivity(final Activity act) {
        AlertDialog alertDialog = new AlertDialog.Builder(
                act).create();
        alertDialog.setCancelable(false);
        alertDialog
                .setTitle("This application requires an internet connection:\n");
        alertDialog
                .setMessage("Please configure your connectivity settings and re-try");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//				GieGieActivity.GIEGIE_ACTIVITY.finish();
//				act.finish();
            }
        });
        alertDialog.show();
    }

    public static void noDataConnectivity(final Activity act) {
        AlertDialog alertDialog = new AlertDialog.Builder(act).create();
        alertDialog.setTitle("Unable to connect to the internet");
        alertDialog
                .setMessage("Your device is not connecting to the internet, please make sure you have a data connection then retry.");
        alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                act.finish();
            }
        });
        alertDialog.show();
    }

    public static void noDataConnectivity1(final Activity act) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(act);
        alertDialog.setTitle("Unable to connect to the internet");
        alertDialog.setCancelable(false);
        alertDialog
                .setMessage("Your device is not connecting to the internet; Please close the app"
                        + " then make sure you have a stable data connection or Retry.");
        alertDialog.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        act.finish();
                    }
                });
        alertDialog.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
