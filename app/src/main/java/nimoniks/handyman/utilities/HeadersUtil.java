package nimoniks.handyman.utilities;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class HeadersUtil
{

	public static void removeNotifBar(Activity activity)
	{
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

}
