package com.pushlab.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import me.pushy.sdk.Pushy;

public class MainActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allow network operations (needed for token generation if not using background thread)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        textView = new TextView(this);
        textView.setText("Initializing Pushy...");
        textView.setTextSize(18);
        textView.setPadding(30, 100, 30, 30);
        setContentView(textView);

        // Initialize Pushy SDK
        Pushy.listen(this);

        // Fetch and display device token
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Get the device token (registers with Pushy)
                    final String deviceToken = Pushy.register(getApplicationContext());

                    // Show it on screen
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("✅ Pushy Device Token:\n\n" + deviceToken);
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("❌ Error registering device:\n" + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }
  }
