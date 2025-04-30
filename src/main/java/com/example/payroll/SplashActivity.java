package com.example.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Button btnAdmin, btnUser;

    private static final String ADMIN_PASSCODE = "admin123"; // Set your admin passcode here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnAdmin = findViewById(R.id.btnAdmin);
        btnUser = findViewById(R.id.btnUser);

        btnAdmin.setOnClickListener(v -> showAdminPasscodeDialog());

        btnUser.setOnClickListener(v -> navigateToLogin());
    }

    private void showAdminPasscodeDialog() {
        AdminPasscodeDialog dialog = new AdminPasscodeDialog(this, ADMIN_PASSCODE, success -> {
            if (success) {
                navigateToAdminDashboard();
            }
        });
        dialog.show();
    }

    private void navigateToAdminDashboard() {
        Intent intent = new Intent(SplashActivity.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish();
    }


    private void navigateToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
