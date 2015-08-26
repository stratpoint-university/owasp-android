package org.owasp.seraphimdroid.receiver;

import org.owasp.seraphimdroid.PasswordActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class WifiStateReceiver extends BroadcastReceiver {
	
	WifiManager wifiManager;
	static Boolean wasOn = false;
	
	public WifiStateReceiver() {}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
		switch (extraWifiState) {
		case android.net.wifi.WifiManager.WIFI_STATE_ENABLED:
			if(wasOn == false) {
				wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		    	wifiManager.setWifiEnabled(false);
		    	showLock(context, "wifi", 0);
			}
			break;
		case android.net.wifi.WifiManager.WIFI_STATE_DISABLED:
			if(wasOn == true) {
				wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		    	wifiManager.setWifiEnabled(true);
		    	showLock(context, "wifi", 1);
			}
			break;
		default:
			break;
		}
	}
	
	private void showLock(final Context context, final String service, final int state) {
		Intent passwordAct = new Intent(context, PasswordActivity.class);
		passwordAct.putExtra("PACKAGE_NAME", "");
		passwordAct.putExtra("service", service);
		passwordAct.putExtra("state", state + "");
		passwordAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(passwordAct);
	}
	
	public static void setStatus(Boolean status) {
		wasOn = status;
	}
	
}
