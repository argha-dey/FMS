package com.cyberswift.fms.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;


import com.cyberswift.fms.R;
import com.cyberswift.fms.callback.AlertDialogCallBack;
import com.cyberswift.fms.model.UserClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	// private static String LOCATIONCLASS = "LOCATIONCLASS";
	private static String USERCLASS = "USERCLASS";
	private static String OFFLINEDATASETLIST = "OFFLINEDATASETLIST";
	private static String ALLDATAFORM = "ALLDATAFORM";
	private static String PREF_NAME="FMFPref";

	public static boolean isInternetPresent(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmailValid(String email) {
		Pattern pattern;
		Matcher matcher;
		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else
			return false;
	}

	public static void hideSoftKeyboard(Context mContext, View mView) {
		if (mView != null) {
			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
		}
	}

	public static String getDeviceID(Context context) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = manager.getDeviceId();
		Log.v("Device ID: ", deviceId);
		return deviceId = (deviceId == null ? "000" : deviceId);
	}

	public static void buildAlertMessageNoGps(final Context context) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});

		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showMessageWithOk(final Context mContext, final String message) {
		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				alert.setTitle(R.string.app_name);

				alert.setMessage(message);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});
				alert.show();
			}
		});
	}

	public static void showCallBackMessageWithOk(final Context mContext, final String message, final AlertDialogCallBack callBack) {
		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				alert.setTitle(R.string.app_name);
				alert.setCancelable(false);
				alert.setMessage(message);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						callBack.onSubmit();
					}
				});
				alert.show();
			}
		});
	}

	public static void showCallBackMessageWithOkCancel(final Context mContext, final String message, final AlertDialogCallBack callBack) {
		((Activity) mContext).runOnUiThread(new Runnable() {

			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
				alert.setTitle(R.string.app_name);
				alert.setCancelable(false);
				alert.setMessage(message);
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						callBack.onSubmit();
					}
				});
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						callBack.onCancel();
					}
				});
				alert.show();
			}
		});
	}

	public static void showMessageWithOkFocus(final Context mContext, final String message, final ScrollView mScrollView, final View mView) {

		final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
		alert.setTitle(R.string.app_name);

		alert.setMessage(message);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				// view.requestFocus();
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						mScrollView.scrollTo(0, mView.getBottom());
					}
				});
			}
		});
		alert.show();
	}

	public static String getBase64StringFromBitmap(Bitmap mBitmap) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
		byte[] ba = bao.toByteArray();
		return (Base64.encodeToString(ba, Base64.DEFAULT));
	}

	public static Bitmap getBitmapBase64FromString(String encodedImage) {
		byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		return (decodedByte);
	}

	public static void trimCache(Context context) {
		try {
			File dir = context.getCacheDir();
			if (dir != null && dir.isDirectory()) {
				deleteDir(dir);
			}
		} catch (Exception e) {

			// TODO: handle exception
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it

		return dir.delete();
	}

	// Saving UserClass details
	public static void saveUserClass(final Context mContext, UserClass userClass) {
		System.out.println("-------------->>    -----------\nSave user Class called\n\n--------------------------->>>");
		SharedPreferences IMISPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = IMISPrefs.edit();
		try {
			prefsEditor.putString(USERCLASS, ObjectSerializer.serialize(userClass));
		} catch (IOException e) {
			e.printStackTrace();
		}
		prefsEditor.commit();
	}

	// Fetching UserClass details
	public static UserClass fetchUserClass(final Context mContext) {
		SharedPreferences IMISPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		UserClass userClass = null;
		String serializeOrg = IMISPrefs.getString(USERCLASS, null);
		try {
			if (serializeOrg != null) {
				userClass = (UserClass) ObjectSerializer.deserialize(serializeOrg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return userClass;
	}

	public static AlertDialog showSettingsAlert(final Context applicationContext, AlertDialog systemAlertDialog) {

		AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
		builder.setTitle("GPS Disabled");
		builder.setCancelable(false);
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// mContext.startActivity(new
				// Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // <-- Newly
																	// added
																	// line
				applicationContext.startActivity(viewIntent);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});
		systemAlertDialog = builder.create();
		systemAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		systemAlertDialog.show();
		return systemAlertDialog;
	}

	public static Calendar getCurrentDate() {

		Calendar currentDate = Calendar.getInstance();
		return currentDate;
	}

	public static String getCurrentFormattedDate() {

		String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

		return formattedDate;
	}
	public static void getTimeFromNetwork(Context mContext) {

	}



}
