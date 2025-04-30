package com.example.payroll;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.payroll.DatabaseManager;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        dbManager = new DatabaseManager(this);

        // Apply underline to tvRegister text
        SpannableString spannableString = new SpannableString("No account? Register");
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        tvRegister.setText(spannableString);

btnLogin.setOnClickListener(v -> {
    String user = etUsername.getText().toString().trim();
    String pass = etPassword.getText().toString().trim();

    Toast.makeText(this, "Attempting login for user: " + user, Toast.LENGTH_SHORT).show();

    if (dbManager.checkUser(user, pass)) {
        if (user.equalsIgnoreCase("admin")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            // Retrieve empId from database or use username as empId if same
            String empId = user; // Assuming username is empId; adjust if different
            Intent intent = new Intent(LoginActivity.this, ViewSalaryActivity.class);
            intent.putExtra("empId", empId);
            startActivity(intent);
        }
        finish();
    } else {
        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
    }
});



        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
