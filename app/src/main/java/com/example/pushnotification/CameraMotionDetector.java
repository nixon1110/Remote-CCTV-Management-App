package com.example.pushnotification;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.IOException;

public class CameraMotionDetector {

    private static final String TAG = "CameraMotionDetector";

    public static void checkMotionDetection(Context context, String username, String password) {
        new MotionDetectionTask(context, username, password).execute();
    }

    private static class MotionDetectionTask extends AsyncTask<Void, Void, String> {

        private final Context context;
        private final String username;
        private final String password;

        public MotionDetectionTask(Context context, String username, String password) {
            this.context = context;
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            String credential = Credentials.basic(username, password);
                            return response.request().newBuilder()
                                    .header("Authorization", credential)
                                    .build();
                        }
                    })
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.168.1.43/cgi-bin/eventManager.cgi?action=getEventIndexes&code=VideoMotion")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Error: " + response.code() + " " + response.message();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error checking motion detection: " + e.getMessage());
                return "Error: " + e.getMessage();
            }
        }

        /*@Override
        protected void onPostExecute(String result) {
            if (result.contains("channels[0]=0")) {
                // Motion detected
                showMotionDetectedMessage();
            } else {
                // No motion detected or error
                Log.d(TAG, "No motion detected or error: " + result);
            }
        }*/

        /*private void showMotionDetectedMessage() {
            // Show motion detected message in the notification activity
            if (context instanceof NotificationActivity) {
                ((NotificationActivity) context).showMotionDetectionMessage();
            }*/

    }
}
