package com.parse.tutorials.pushnotifications;

import com.parse.Parse;
import com.parse.PushService;

public class Application extends android.app.Application {

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		String YOUR_APP_ID = "9RE2aSEEW7fPXgdZneuusGDhwOWWPpHRXsGVPj9K";
		String YOUR_CLIENT_KEY = "hWyYopws8otPzkL3MSDg46rizwrcS4t3fAIfMvac";
		Parse.initialize(this, YOUR_APP_ID, YOUR_CLIENT_KEY);

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);
	}
}