package com.example.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etEmpId, etName, etDesignation, etTotalDays, etWorkedDays, etSalaryPerDay, etAllowances, etDeductions, etNetSalary;
    Button btnCalculate, btnSave, btnBack;

    private static final String TAG = "PayrollApp";

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmpId = findViewById(R.id.etEmpId);
        etName = findViewById(R.id.etName);
        etDesignation = findViewById(R.id.etDesignation);
        etTotalDays = findViewById(R.id.etTotalDays);
        etWorkedDays = findViewById(R.id.etWorkedDays);
        etSalaryPerDay = findViewById(R.id.etSalaryPerDay);
        etAllowances = findViewById(R.id.etAllowances);
        etDeductions = findViewById(R.id.etDeductions);
        etNetSalary = findViewById(R.id.etNetSalary);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        db = new DatabaseHelper(this);

        btnCalculate.setOnClickListener(v -> {
            try {
                if (etTotalDays.getText().toString().isEmpty() ||
                        etWorkedDays.getText().toString().isEmpty() ||
                        etSalaryPerDay.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalDays = Integer.parseInt(etTotalDays.getText().toString());
                int workedDays = Integer.parseInt(etWorkedDays.getText().toString());
                double salaryPerDay = Double.parseDouble(etSalaryPerDay.getText().toString());
                double allowances = etAllowances.getText().toString().isEmpty() ? 0 : Double.parseDouble(etAllowances.getText().toString());
                double deductions = etDeductions.getText().toString().isEmpty() ? 0 : Double.parseDouble(etDeductions.getText().toString());

                if (totalDays <= 0 || workedDays < 0 || workedDays > totalDays) {
                    Toast.makeText(this, "Worked days must be between 0 and Total Days", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (salaryPerDay <= 0) {
                    Toast.makeText(this, "Salary per Day must be positive", Toast.LENGTH_SHORT).show();
                    return;
                }

                double net = (workedDays * salaryPerDay) + allowances - deductions;
                if (net < 0) {
                    Toast.makeText(this, "Net salary cannot be negative", Toast.LENGTH_SHORT).show();
                    return;
                }

                etNetSalary.setText(String.format("₹%.2f", net));
                android.util.Log.d(TAG, "Calculated Net Salary: ₹" + net);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid numeric input", Toast.LENGTH_SHORT).show();
                android.util.Log.e(TAG, "Error calculating salary: " + e.getMessage());
            }
        });

        btnSave.setOnClickListener(v -> {
            try {
                if (etEmpId.getText().toString().isEmpty() ||
                        etName.getText().toString().isEmpty() ||
                        etDesignation.getText().toString().isEmpty() ||
                        etTotalDays.getText().toString().isEmpty() ||
                        etNetSalary.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please fill all fields and calculate salary first", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean inserted = db.insertEmployee(
                        etEmpId.getText().toString(),
                        etName.getText().toString(),
                        etDesignation.getText().toString(),
                        Integer.parseInt(etTotalDays.getText().toString()),
                        Double.parseDouble(etSalaryPerDay.getText().toString()),
                        Integer.parseInt(etWorkedDays.getText().toString()),
                        etAllowances.getText().toString().isEmpty() ? 0 : Double.parseDouble(etAllowances.getText().toString()),
                        etDeductions.getText().toString().isEmpty() ? 0 : Double.parseDouble(etDeductions.getText().toString()),
                        Double.parseDouble(etNetSalary.getText().toString().replace("₹", ""))
                );

                if (!inserted) {
                    // Try update if insert failed (likely due to duplicate emp_id)
                    boolean updated = db.updateEmployee(
                            etEmpId.getText().toString(),
                            etName.getText().toString(),
                            etDesignation.getText().toString(),
                            Integer.parseInt(etTotalDays.getText().toString()),
                            Double.parseDouble(etSalaryPerDay.getText().toString()),
                            Integer.parseInt(etWorkedDays.getText().toString()),
                            etAllowances.getText().toString().isEmpty() ? 0 : Double.parseDouble(etAllowances.getText().toString()),
                            etDeductions.getText().toString().isEmpty() ? 0 : Double.parseDouble(etDeductions.getText().toString()),
                            Double.parseDouble(etNetSalary.getText().toString().replace("₹", ""))
                    );
                    if (updated) {
                        Toast.makeText(this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                        android.util.Log.d(TAG, "Employee record updated successfully.");
                    } else {
                        Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show();
                        android.util.Log.e(TAG, "Failed to insert or update employee data.");
                    }
                } else {
                    Toast.makeText(this, "Record saved successfully", Toast.LENGTH_SHORT).show();
                    clearForm();
                    android.util.Log.d(TAG, "Employee record saved successfully.");
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                android.util.Log.e(TAG, "Error saving employee record: " + e.getMessage());
            }
        });


        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void clearForm() {
        etEmpId.setText("");
        etName.setText("");
        etDesignation.setText("");
        etTotalDays.setText("");
        etWorkedDays.setText("");
        etSalaryPerDay.setText("");
        etAllowances.setText("0");
        etDeductions.setText("0");
        etNetSalary.setText("");
    }
}
