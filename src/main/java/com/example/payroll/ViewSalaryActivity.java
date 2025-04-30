package com.example.payroll;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewSalaryActivity extends AppCompatActivity {
    TextView tvDetails;
    DatabaseHelper dbHelper;
    Button btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salary);

        tvDetails = findViewById(R.id.tvDetails);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        dbHelper = new DatabaseHelper(this);

        try {
            String empId = getIntent().getStringExtra("empId");
            String username = empId; // default to empId if username not passed

            // Try to get username from intent extras if available
            if (getIntent().hasExtra("username")) {
                username = getIntent().getStringExtra("username");
            }

            Toast.makeText(this, "Received username: " + username, Toast.LENGTH_LONG).show();

            if (username == null || username.isEmpty()) {
                tvDetails.setText("Username is missing.");
                return;
            }

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // First, get empId for the username
            Cursor empIdCursor = db.rawQuery("SELECT emp_id FROM employees WHERE name = ?", new String[]{username.trim()});
            String actualEmpId = null;
            if (empIdCursor.moveToFirst()) {
                actualEmpId = empIdCursor.getString(empIdCursor.getColumnIndexOrThrow("emp_id"));
            }
            empIdCursor.close();

            if (actualEmpId == null) {
                tvDetails.setText("No employee ID found for username: " + username);
                Toast.makeText(this, "No employee ID found for username: " + username, Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this, "Resolved empId: " + actualEmpId, Toast.LENGTH_LONG).show();

            // Now fetch salary details using actual empId
            Cursor cursor = db.rawQuery("SELECT * FROM employees WHERE emp_id = ?", new String[]{actualEmpId.trim()});

            if (cursor.moveToFirst()) {
                String details = "ID: " + cursor.getString(cursor.getColumnIndexOrThrow("emp_id")) +
                        "\nName: " + cursor.getString(cursor.getColumnIndexOrThrow("name")) +
                        "\nDesignation: " + cursor.getString(cursor.getColumnIndexOrThrow("designation")) +
                        "\nTotal Days: " + cursor.getInt(cursor.getColumnIndexOrThrow("total_days")) +
                        "\nSalary per Day: ₹" + cursor.getDouble(cursor.getColumnIndexOrThrow("salary_per_day")) +
                        "\nWorked Days: " + cursor.getInt(cursor.getColumnIndexOrThrow("worked_days")) +
                        "\nAllowances: ₹" + cursor.getDouble(cursor.getColumnIndexOrThrow("allowances")) +
                        "\nDeductions: ₹" + cursor.getDouble(cursor.getColumnIndexOrThrow("deductions")) +
                        "\nNet Salary: ₹" + cursor.getDouble(cursor.getColumnIndexOrThrow("net_salary"));

                tvDetails.setText(details);
                Toast.makeText(this, "Record found for empId: " + actualEmpId, Toast.LENGTH_LONG).show();

                // Additional debug: show all employees details
                String allEmployees = dbHelper.getAllEmployeesDebugString();
                Toast.makeText(this, "All employees:\n" + allEmployees, Toast.LENGTH_LONG).show();
            } else {
                tvDetails.setText("No records found.");
                Toast.makeText(this, "No records found for empId: " + actualEmpId, Toast.LENGTH_LONG).show();

                // Additional debug: show all employees details
                String allEmployees = dbHelper.getAllEmployeesDebugString();
                Toast.makeText(this, "All employees:\n" + allEmployees, Toast.LENGTH_LONG).show();
            }





            cursor.close();
        } catch (Exception e) {
            tvDetails.setText("Error loading salary details: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }



        // Set click listener for back to login button
        if (btnBackToLogin != null) {
            btnBackToLogin.setOnClickListener(v -> {
                Intent intent = new Intent(ViewSalaryActivity.this, SplashActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            });
        }

    }
}
