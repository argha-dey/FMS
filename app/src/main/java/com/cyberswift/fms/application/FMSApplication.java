package com.cyberswift.fms.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;

import com.cyberswift.fms.R;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(mailTo = "ritwikrai04@gmail.com", customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
		ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT }, forceCloseDialogAfterToast = false, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast)
public class FMSApplication extends Application{

	public static FMSApplication mInstance;
	public static PendingIntent pendingIntent;
	public static AlarmManager alarmManager;
	private static boolean activityVisible;
	String interval = "";
	private int REQUEST_CODE = 43668;
	private String TAG = "FMSApplication";

	public static boolean isActivityVisible() {

		return activityVisible;
	}

	public static void activityResumed() {

		activityVisible = true;
	}

	public static void activityPaused() {

		activityVisible = false;
	}

	public static synchronized FMSApplication getInstance() {

		return mInstance;
	}

	/**
	 * returns the pending intent that fires the alarm manager
	 * 
	 * @return {@link PendingIntent}
	 **/
	public static PendingIntent getAlarmPI() {
		return pendingIntent;
	}

	/** Returns the alarm Manager **/
	public static AlarmManager getAlarmManager() {

		return alarmManager;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		ACRA.init(this);

	}

//	//***
//			* Start alarm Manager with user driven interval .
//	* @param activity - {@link Activity} **//*
//	public void startAlarmManager(Activity activity) {
//
//		try {
//			interval = Util.getSharedPreference(getApplicationContext()).getString(tag_tracking_interval, "30");
//			Log.i(TAG, "Tracking interval:: " + interval);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// ------------------ DEFAULT --------------------
//		final long TRACKING_INTERVAL = Integer.parseInt(interval) * 60000;
//		// ----------------------CUSTOM-----------------------
//		//final long TRACKING_INTERVAL = 3 * 60000;
//		Intent myIntent = new Intent(activity, MyReceiver.class);
//		pendingIntent = PendingIntent.getBroadcast(activity, REQUEST_CODE, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		Calendar calendar = Calendar.getInstance();
//		alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis() + TRACKING_INTERVAL, pendingIntent);
//		String currentDateTime = Util.getDateTime();
//		Log.v("FMS Application", "Current Date Time: " + currentDateTime);
//
//		try {
//			SharedPreferences.Editor editor = Util.getIsNotificationRunningStatusSharedEditor(getApplicationContext());
//			editor.putString(tag_alarmStartTime, currentDateTime).commit();
//			Log.v("FMS Application",
//					"Alarm start time: " + Util.getIsNotificationRunningActiveStatusSharedPreference(getApplicationContext()).getString(tag_alarmStartTime, ""));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	*//** Start Alarm Manager when Tracking is active but the user has switched off the device.<p>
//			* @param activity
//	* @param remainingTime - in milliseconds**//*
//	public void startAlarmManager(Activity activity, long remainingTime) {
//
//		Log.v("FMSApplication", "Start Alarm Manager-- with remaining Time. " + remainingTime);
//
//		final long TRACKING_INTERVAL = remainingTime;
//
//		Intent myIntent = new Intent(activity, MyReceiver.class);
//		pendingIntent = PendingIntent.getBroadcast(activity, REQUEST_CODE, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//		Calendar calendar = Calendar.getInstance();
//		alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE); //
//		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis() + TRACKING_INTERVAL, pendingIntent);
//
//		String currentDateTime = Util.getDateTime();
//		Log.v("FMS Application", "Current Date Time: " + currentDateTime);
//
//		try {
//			SharedPreferences.Editor editor = Util.getIsNotificationRunningStatusSharedEditor(getApplicationContext());
//			editor.putString(tag_alarmStartTime, currentDateTime).commit();
//			Log.v("FMS Application",
//					"Alarm start time: " + Util.getIsNotificationRunningActiveStatusSharedPreference(getApplicationContext()).getString(tag_alarmStartTime, ""));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	*//**
//			* End the Alarm Manager **//*
//	public void endAlarmManager() {
//
//		Log.v("FMSApplication", "End Alarm Manager ");
//
//		if (alarmManager != null) {
//			pendingIntent.cancel();
//			alarmManager.cancel(pendingIntent);
//		}
//	}*/

}
