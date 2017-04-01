package nimoniks.handyman.utilities;

import android.net.Uri;

import java.util.List;
import java.util.Vector;

public class StringManagerUtil {
    public static int MAXIMUM_RECIPIENTS = 77;
    public static String ASTERIK = "*";
    public static String HASH_TAG = Uri.encode("#");
    public static String SPACE = " ";
    public static String TAB2 = "\t\t";

    // STRING MANAGER
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static String beforeSymbol(String data) {
        int index = data.indexOf(':');
        return data.substring(0, index);
    }

    public static String afterSymbol(String data) {
        int index = data.indexOf(':');
        return data.substring(index + 1, data.length());
    }

    public static String unpackBal(String data) {
        data = removeQuotes(data);
        int index = data.indexOf(':');
        // String setUp = data.substring(0, index);
        String bal = data.substring(index + 1, data.length());
        return bal;
    }

    public static Vector<String> unpack(String data) {
        // Remove ""
        StringBuffer str = new StringBuffer(data);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                str.deleteCharAt(i);
            }
        }
        data = str.toString();

        // Get Error and Value
        Vector<String> vector = new Vector<String>();
        int index = data.indexOf(':');
        vector.add(data.substring(0, index));
        vector.add(data.substring(index + 1, data.length()));
        return vector;
    }

    // Method to delete spaces within message and replace with "+"
    public static String replaceSpacesWithPlus(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                str.insert(i, '+');
            }
        }
        return str.toString();
    }

    // Method to delete spaces within message and replace with "+"
    public static String replaceSpecialXters(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '*') {
                str.deleteCharAt(i);
                str.insert(i, ',');
            }

        }
        return str.toString();
    }

    // Method to delete spaces within message and replace with "%20"
    public static String replaceSpacesWithPercent20(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                str.insert(i, "%20");
            }
        }
        return str.toString();
    }

    // Method to delete spaces within EMAILS
    public static String removeSpaces(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
            }
        }
        return str.toString();
    }

    // Method to delete spaces within EMAILS
    public static String removePlus(String s) {
        // Log.d("", "Formatting . . ." + s);

        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+') {
                str.deleteCharAt(i);
            }
        }
        return str.toString();
    }

    // Method to delete quotes within EMAILS
    public static String removeQuotes(String s) {
        // Log.d("", "Formatting . . ." + s);

        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '"') {
                str.deleteCharAt(i);
            }
        }
        return str.toString();
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Method to delete Special Xters
    public static String removeSpecialXters(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ' || str.charAt(i) == '-'
                    || str.charAt(i) == '\n' || str.charAt(i) == ','
                    || str.charAt(i) == '+') {
                str.deleteCharAt(i);
            }
        }
        return str.toString();
    }

    // Method to delete Special Xters
    public static String removeLastComma(String s) {
        StringBuffer str = new StringBuffer(s);

        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

//    public static String createCSV(List<Recipient> recipientList) {
//        StringBuffer recipientCSV = new StringBuffer();
//
//        for (int i = 0; i < recipientList.size(); i++) {
//            Recipient recipient = recipientList.get(i);
//
//            if (recipient.getContact().equals(""))
//                continue;
//
//            recipientCSV.append(recipient.getContact().concat(","));
//        }
//        return recipientCSV.toString();
//    }

    public static String removeComma(String s) {
        StringBuffer str = new StringBuffer(s);
        // Read each character and replace if it�s a space
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                str.deleteCharAt(i);
            }
        }
        return str.toString();
    }

    public static String removeLastSpace(String s) {
        s = s.substring(0, s.length() - 1);
        return s;
    }

    public static String removeLeadingZero(String s) {
        s = s.substring(1, s.length());
        return s;
    }

    public static String replaceHash(String s) {
        StringBuffer str = new StringBuffer(s);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '#') {
                str.deleteCharAt(i);
                str.insert(i, "%23");
            }
        }
        return str.toString();
    }
}
