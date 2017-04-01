package nimoniks.handyman.utilities;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil
{
	public static void makeToast(String message, Activity act)
	{
		// with jam obviously
		Toast.makeText(act, "\n" + message + "\n", Toast.LENGTH_SHORT).show();
	}

	public static void makeLongToast(CharSequence charSequence, Activity act)
	{
		// with jam obviously
		Toast.makeText(act, "\n" + charSequence + "\n", Toast.LENGTH_LONG)
				.show();
	}
}
