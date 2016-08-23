package com.agriscienceapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.agriscienceapp.activity.MainSplashActivity;


@SuppressWarnings("static-access")
public class SessionManager {
	
	private static final String PREF_PRIVATE = "AgriScience";
	
	public static String getRegistrationId(Context context) {
		String registrationId = "";
		try {
			SharedPreferences prefs = context.getSharedPreferences(MainSplashActivity.class.getSimpleName(),
	                Context.MODE_PRIVATE);
			registrationId = prefs.getString(MainSplashActivity.PROPERTY_REG_ID, "ERROR");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registrationId;
	}



//	public static void setIsUserLoggedin(Context context, boolean val) {
//		try {
//			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//			Editor editor = preferences.edit();
//			editor.putBoolean(SessionKeys.SHARED_ISLOGGEDIN, val);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static boolean getIsUserLoggedin(Context context) {
//		// Integer val = 0;
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		boolean val = preferences.getBoolean(SessionKeys.SHARED_ISLOGGEDIN, false);
//		return val;
//	}
//
//	public static Integer getUsersId(Context context) {
//		Integer val = 0;
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		val = preferences.getInt(SessionKeys.SHARED_KEY_USER_ID, 0);
//		return val;
//	}
//
//	// ///postcode
//	public static void saveUserPostCode(Context context, String val) {
//		try {
//			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//			Editor editor = preferences.edit();
//			editor.putString(SessionKeys.SHARED_KEY_POSTCODE, val);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static String getUsersPostCode(Context context) {
//		String postalCode = "";
//		try {
//			SharedPreferences preferences = PreferenceManager
//					.getDefaultSharedPreferences(context);
//			postalCode = preferences.getString(SessionKeys.SHARED_KEY_POSTCODE, "");
//			Log.i("postalCode", "::: " + postalCode);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return postalCode;
//	}

	///notification
	public static void setNotificationONOff(Context context, Boolean val)
	{
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = preferences.edit();
			editor.putBoolean(SessionKeys.SHARED_KEY_NOTIFICATION_ON_OFF, val);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Boolean getNotificationONOff(Context context) {
		Boolean val = false;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		val = preferences.getBoolean(
				SessionKeys.SHARED_KEY_NOTIFICATION_ON_OFF, false);
		return val;
	}

//	public static void storePostalCodeLatLong(Context applicationContext, String lat,
//			String lng) {
//		try {
//			SharedPreferences preferences = PreferenceManager
//					.getDefaultSharedPreferences(applicationContext);
//			Editor editor = preferences.edit();
//			editor.putString(SessionKeys.SHARED_KEY_LATITUDE, lat.toString());
//			editor.putString(SessionKeys.SHARED_KEY_LONG, lng.toString());
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static String getPostalCodeLat(Context context) {
//		String val = null;
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		val = preferences.getString(SessionKeys.SHARED_KEY_LATITUDE, "0.0");
//		System.out.println(" lat ::: " + val);
//		return val;
//	}
//
//	public static String getPostalCodeLng(Context context) {
//		String val = null;
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		val = preferences.getString(SessionKeys.SHARED_KEY_LONG, "0.0");
//		System.out.println(" lng ::: " + val);
////		Log.i("val ", "val" + val);
//		return val;
//	}
	
	
	public static String getLatitude(Activity act) {
		String Latitude = "";
		try {
			SharedPreferences sp = act.getSharedPreferences("myCurrentLocation", act.MODE_PRIVATE);
			Latitude = sp.getString("latitude", "");
//			System.out.println("Latitude :::: " + Latitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Latitude;
	}

	public static String getLongitude(Activity act) {
		String Longitude = "";
		try {
			SharedPreferences sp = act.getSharedPreferences("myCurrentLocation", act.MODE_PRIVATE);
			Longitude = sp.getString("longitude", "");
//			System.out.println("Longitude :::: " + Longitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Longitude;
	}

//	public static String getCountryName(Context context) {
//		String val = "";
//		SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//		val = preferences.getString(SessionKeys.SHARED_KEY_COUNTRY_NAME, "");
////		Log.i("CountryName", "Get CountryName::: " + val);
//		return val;
//	}
//
//	public static void saveCountryName(Context context, String strCountryName) {
//		try {
////			Log.i("CountryName", "Set CountryName::: " + strCountryName);
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			Editor editor = preferences.edit();
//			editor.putString(SessionKeys.SHARED_KEY_COUNTRY_NAME, strCountryName);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
//	public static String getCityName(Context context) {
//		String val = "";
//		SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//		val = preferences.getString(SessionKeys.SHARED_KEY_CITY_NAME, "");
////		Log.i("CityName", "Get CityName::: " + val);
//		return val;
//	}
//
//	public static void saveCityName(Context context, String strCityName) {
//		try {
////			Log.i("CountryName", "Set CountryName::: " + strCountryName);
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			Editor editor = preferences.edit();
//			editor.putString(SessionKeys.SHARED_KEY_CITY_NAME, strCityName);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
//	public static String getDefaultSearchRadius(Context context) {
//		String val = "";
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		val = preferences.getString(SessionKeys.SHARED_KEY_SEARCH_RADIUS, "");
//		return val;
//	}
//
//	public static void setDefaultSearchRadius(Context context, String defaultRadius) {
//		try {
//			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//			Editor editor = preferences.edit();
//			editor.putString(SessionKeys.SHARED_KEY_SEARCH_RADIUS, defaultRadius);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void storeRememberMeData(Context context, boolean isRemeber, String strUserName, String strPassword) {
//		try {
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			Editor editor = preferences.edit();
//			editor.putBoolean(SessionKeys.SHARED_KEY_REMEMBER, isRemeber);
//			editor.putString(SessionKeys.SHARED_KEY_REMEMBER_USER_NAME, strUserName);
//			editor.putString(SessionKeys.SHARED_KEY_REMEMBER_PASSWORD, strPassword);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static boolean isRemember(Context context) {
//		try {
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			return preferences.getBoolean(SessionKeys.SHARED_KEY_REMEMBER, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	public static String getRememberUserName(Context context){
//		try {
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			return preferences.getString(SessionKeys.SHARED_KEY_REMEMBER_USER_NAME, "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
//
//	public static String getRememberPassword(Context context){
//		try {
//			SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_PRIVATE, Context.MODE_PRIVATE);
//			return preferences.getString(SessionKeys.SHARED_KEY_REMEMBER_PASSWORD, "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//	}
	
	public static void setNotificationBadge(Context context, int val)
	{
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = preferences.edit();
			editor.putInt(SessionKeys.SHARED_KEY_NOTIFICATION_BADGE, val);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getNotifationBadge(Context context) {		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);		
		return preferences.getInt(SessionKeys.SHARED_KEY_NOTIFICATION_BADGE, 0);
	}
	
	
//	public static void setFromLogin(Context context, boolean val)
//	{
//		try {
//			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//			Editor editor = preferences.edit();
//			editor.putBoolean(SessionKeys.SHARED_KEY_FROM_LOGIN, val);
//			editor.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static boolean getFromLogin(Context context) {
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		return preferences.getBoolean(SessionKeys.SHARED_KEY_FROM_LOGIN, false);
//	}

	public static void clearAppCredential(Context context) {
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor editor = preferences.edit();
			editor.clear();
			editor.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}