package com.parse.tutorials.pushnotifications;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseBroadcastReceiver;

public class CustomPushReceiver extends ParseBroadcastReceiver {

	public static final String ACTION = "com.parse.ParseBroadcastReceiver.CustomPushReceiver";
	private static final String TAG = CustomPushReceiver.class.getSimpleName();

	public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";
	public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Channel";

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		String action = intent.getAction();

		if (action.equals(ACTION)) {
			try {
				String channel = intent.getExtras().getString(
						PARSE_JSON_CHANNELS_KEY);

				JSONObject json = new JSONObject(intent.getExtras().getString(
						PARSE_EXTRA_DATA_KEY));

				Log.e(TAG, json.toString());
				Toast.makeText(context, json.toString(), Toast.LENGTH_SHORT)
						.show();

				showNotificationMessage(context, channel,
						json.getString("customdata"), intent);

			} catch (JSONException e) {
				Log.d(TAG, "JSONException: " + e.getMessage());
			}
		}

	}

	public void showNotificationMessage(Context mContext, String title,
			String message, Intent intent) {

		int icon = R.drawable.ic_launcher;
		int mNotificationId = 100;

		PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
				0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				mContext);
		Notification notification = mBuilder
				.setSmallIcon(icon)
				.setTicker(title)
				.setWhen(0)
				.setAutoCancel(true)
				.setContentTitle(title)
				.setStyle(inboxStyle)
				.setContentIntent(resultPendingIntent)
				.setSound(
						RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setLargeIcon(
						BitmapFactory.decodeResource(mContext.getResources(),
								icon)).setContentText(message).build();

		NotificationManager notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(mNotificationId, notification);
	}
}
