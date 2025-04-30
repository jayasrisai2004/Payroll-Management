package com.example.payroll;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.payroll.adapter.EmployeeAdapter;
import com.example.payroll.model.Employee;
import java.util.List;

public class AdminDatabaseViewActivity extends AppCompatActivity {

    private Button btnBack;
    private RecyclerView recyclerViewSalaries;
    private EmployeeAdapter employeeAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_database_view);

        btnBack = findViewById(R.id.btnBack);
        recyclerViewSalaries = findViewById(R.id.recyclerViewSalaries);
        dbHelper = new DatabaseHelper(this);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        loadSalariesFromDatabase();
    }


    private void loadSalariesFromDatabase() {
        List<Employee> salaryList = dbHelper.getAllEmployeesList();
        employeeAdapter = new EmployeeAdapter(this, salaryList);
        recyclerViewSalaries.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSalaries.setAdapter(employeeAdapter);
    }
}
