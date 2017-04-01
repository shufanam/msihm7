package nimoniks.handyman.utilities;

import android.util.Log;

public class LoggerUtil
{
	public static void log(String string)
	{
		Log.d("", ":::::::::::::::::::::: " + string);
	}

	public static void log(String name, String string)
	{
		Log.d(name, ":::::::::::::::::::::::::::::::::::::::::::::::: " + string);
	}
}
