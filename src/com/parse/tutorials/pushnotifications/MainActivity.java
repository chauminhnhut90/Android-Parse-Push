package com.parse.tutorials.pushnotifications;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ParseAnalytics.trackAppOpened(getIntent());

		registerChannel();

	}

	private void registerChannel() {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.addAllUnique("channels", Arrays.asList("CHANNEL_1",
				"CHANNEL_2", "CHANNEL_3", "CHANNEL_4"));
		installation.saveInBackground();
	}

	public void click(View c) {
		sendPush("CHANNEL_1");
	}

	public void sendPush(String channelName) {
		ParsePush push = new ParsePush();
		push.setChannel(channelName);
		push.setMessage("This message for channel: " + channelName);
		push.sendInBackground();
	}

	public void sendMessageAsIntent(View v) {
		JSONObject data = getJSONDataMessageForIntent();
		
		ParsePush push = new ParsePush();
		push.setChannel("CHANNEL_1");
		push.setData(data);
		push.sendInBackground();
	}

	// Notice how the 'action' attribute enables the
	// broadcast receiver behavior.
	private JSONObject getJSONDataMessageForIntent() {
		try {
			JSONObject data = new JSONObject();
			// Notice alert is not required
			// data.put("alert", "Message from Intent");
			
			// instead action is used
			data.put("action", CustomPushReceiver.ACTION);
			data.put("customdata", "custom data value");
			return data;
		} catch (JSONException x) {
			throw new RuntimeException("Something wrong with JSON", x);
		}
	}
}
