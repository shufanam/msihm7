package nimoniks.handyman.utilities;

/**
 * Created by Isama on 2/22/2017.
 */

public class SessionUtil {

    String appVersion;

    static SessionUtil sessionUtil;

    public static SessionUtil getInstance() {
        if (sessionUtil == null) {
            synchronized (SessionUtil.class) {
                sessionUtil = new SessionUtil();
            }
        }
        return sessionUtil;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }


}
