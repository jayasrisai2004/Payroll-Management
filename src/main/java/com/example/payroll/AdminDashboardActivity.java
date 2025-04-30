package com.example.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnViewDatabase, btnManagePayroll, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        btnViewDatabase = findViewById(R.id.btnViewDatabase);
        btnManagePayroll = findViewById(R.id.btnManagePayroll);
        btnLogout = findViewById(R.id.btnLogout);

        btnViewDatabase.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminDatabaseViewActivity.class);
            startActivity(intent);
        });

        btnManagePayroll.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

}
