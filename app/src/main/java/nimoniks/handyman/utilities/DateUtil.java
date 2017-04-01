package nimoniks.handyman.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{

	public static String longToStringDMY(long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy",
				Locale.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a",
		// Locale.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/EE/yyyy hh:mm a",
		// Locale.getDefault());
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String longToStringDMY2(long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy",
				Locale.getDefault());
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String longToStringDMYHMSA(long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy h:mm:ss a",
				Locale.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a",
		// Locale.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/EE/yyyy hh:mm a",
		// Locale.getDefault());
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static String longToString2(long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy",
				Locale.getDefault());
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static long formatLongDate(long date)
	{
		Date fdate = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy",
				Locale.getDefault());
		long formattedDate = 0;
		try
		{
			formattedDate = sdf.parse(sdf.format(fdate)).getTime();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return formattedDate;
	}

	public static String longToStringDateTime(long date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS",
				Locale.getDefault());

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a",
		// Locale.getDefault());
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/EE/yyyy hh:mm a",
		// Locale.getDefault());
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public static long stringToLongDate(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS",
				Locale.getDefault());
//		String formattedDate = longToStringDMY

		Date longDate = null;
		try {
			longDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return longDate.getTime();
	}
}
