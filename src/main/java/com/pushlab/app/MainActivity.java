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

        // Allow network operations on main thread (only for testing)
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        textView = new TextView(this);
        textView.setText("Initializing Pushy...");
        textView.setTextSize(18f);
        textView.setPadding(40, 120, 40, 40);
        setContentView(textView);

        // Start Pushy background listener
        Pushy.listen(this);

        // Register in background thread
        new Thread(() -> {
            try {
                String deviceToken = Pushy.register(getApplicationContext());

                runOnUiThread(() ->
                        textView.setText("✅ Pushy Device Token:\n\n" + deviceToken)
                );
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        textView.setText("❌ Error registering device:\n" + e.getMessage())
                );
            }
        }).start();
    }
                              }
