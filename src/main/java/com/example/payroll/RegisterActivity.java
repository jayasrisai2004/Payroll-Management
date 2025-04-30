package com.example.payroll;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.payroll.DatabaseManager;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmpId, etUsername, etPassword;
    Button btnRegister;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmpId = findViewById(R.id.etRegEmpId);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);
        dbManager = new DatabaseManager(this);


        btnRegister.setOnClickListener(v -> {
            String empId = etEmpId.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (empId.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean insertedUser = dbManager.insertUser(username, password);
            boolean insertedEmployee = false;
            if (insertedUser) {
                // Insert employee data into payroll.db
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                insertedEmployee = dbHelper.insertEmployee(
                    empId,   // empId from input
                    username, // name (using username as name for now)
                    "",       // designation
                    0,        // totalDays
                    0.0,      // salaryPerDay
                    0,        // workedDays
                    0.0,      // allowances
                    0.0,      // deductions
                    0.0       // netSalary
                );
            }

            if (insertedUser && insertedEmployee) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
