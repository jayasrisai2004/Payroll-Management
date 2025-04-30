package com.example.payroll;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.payroll.adapter.EmployeeAdapter;
import com.example.payroll.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private Button btnBack;
    private RecyclerView recyclerViewUsers;
    private EmployeeAdapter employeeAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        btnBack = findViewById(R.id.btnBack);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(v -> onBackPressed());

        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        List<Employee> employeeList = dbHelper.getAllEmployeesList();
        List<Employee> filteredList = new ArrayList<>();
        for (Employee emp : employeeList) {
            filteredList.add(new Employee(emp.getEmpId(), emp.getName(), "", 0, 0, 0.0, 0.0, 0.0, emp.getNetSalary()));
        }
        employeeAdapter = new EmployeeAdapter(this, filteredList);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(employeeAdapter);
    }
}
