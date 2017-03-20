package com.estpress;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.app.ActivityManager;

import android.content.Context;

import android.os.Handler;

import java.util.List;

public class DetectScreenshot extends CordovaPlugin {

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Activity activity = this.cordova.getActivity();
		DetectScreenshotService(activity, webView);
	}
	
	// http://stackoverflow.com/questions/29532502/detect-a-screenshot-android
	public void DetectScreenshotService(final Activity activity, final CordovaWebView webView) {
	
		final Handler h = new Handler();
		final int delay = 3000;
		final ActivityManager am=(ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
	
		h.postDelayed(new Runnable(){
			public void run(){
	
				List<ActivityManager.RunningServiceInfo> rs=am.getRunningServices(256);
	
				for(ActivityManager.RunningServiceInfo ar:rs){
					if(ar.process.equals("com.android.systemui:screenshot")){
						webView.loadUrl("javascript:cordova.fireDocumentEvent('screenshot');");
						break;
					}
				}
				h.postDelayed(this, delay);
			}
		}, delay);
	
	}	
}





