package com.example.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.payroll.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "payroll.db";
    public static final String TABLE = "employees";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "emp_id TEXT UNIQUE, " +
                "name TEXT, " +
                "designation TEXT, " +
                "total_days INTEGER, " +
                "salary_per_day REAL, " +
                "worked_days INTEGER, " +
                "allowances REAL, " +
                "deductions REAL, " +
                "net_salary REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

    public boolean insertEmployee(String empId, String name, String designation, int totalDays,
                                  double salaryPerDay, int workedDays, double allowances,
                                  double deductions, double netSalary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("emp_id", empId);
        cv.put("name", name);
        cv.put("designation", designation);
        cv.put("total_days", totalDays);
        cv.put("salary_per_day", salaryPerDay);
        cv.put("worked_days", workedDays);
        cv.put("allowances", allowances);
        cv.put("deductions", deductions);
        cv.put("net_salary", netSalary);
        long res = db.insert(TABLE, null, cv);
        return res != -1;
    }

    // New method to update employee record by empId
    public boolean updateEmployee(String empId, String name, String designation, int totalDays,
                                  double salaryPerDay, int workedDays, double allowances,
                                  double deductions, double netSalary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("designation", designation);
        cv.put("total_days", totalDays);
        cv.put("salary_per_day", salaryPerDay);
        cv.put("worked_days", workedDays);
        cv.put("allowances", allowances);
        cv.put("deductions", deductions);
        cv.put("net_salary", netSalary);
        int rows = db.update(TABLE, cv, "emp_id = ?", new String[]{empId});
        return rows > 0;
    }


    // New method to fetch all employees as List<Employee>
    public List<Employee> getAllEmployeesList() {
        List<Employee> employeeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, emp_id, name, designation, total_days, salary_per_day, worked_days, allowances, deductions, net_salary FROM " + TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                String empId = cursor.getString(cursor.getColumnIndexOrThrow("emp_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String designation = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
                int totalDays = cursor.getInt(cursor.getColumnIndexOrThrow("total_days"));
                double salaryPerDay = cursor.getDouble(cursor.getColumnIndexOrThrow("salary_per_day"));
                int workedDays = cursor.getInt(cursor.getColumnIndexOrThrow("worked_days"));
                double allowances = cursor.getDouble(cursor.getColumnIndexOrThrow("allowances"));
                double deductions = cursor.getDouble(cursor.getColumnIndexOrThrow("deductions"));
                double netSalary = cursor.getDouble(cursor.getColumnIndexOrThrow("net_salary"));

                if (designation == null) designation = "";
                employeeList.add(new Employee(empId, name, designation, totalDays, workedDays, salaryPerDay, allowances, deductions, netSalary));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return employeeList;
    }

    // New method to fetch employee by empId
    public Employee getEmployeeByEmpId(String empId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, emp_id, name, designation, total_days, salary_per_day, worked_days, allowances, deductions, net_salary FROM " + TABLE + " WHERE emp_id = ?", new String[]{empId});
        Employee employee = null;
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("emp_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String designation = cursor.getString(cursor.getColumnIndexOrThrow("designation"));
            int totalDays = cursor.getInt(cursor.getColumnIndexOrThrow("total_days"));
            double salaryPerDay = cursor.getDouble(cursor.getColumnIndexOrThrow("salary_per_day"));
            int workedDays = cursor.getInt(cursor.getColumnIndexOrThrow("worked_days"));
            double allowances = cursor.getDouble(cursor.getColumnIndexOrThrow("allowances"));
            double deductions = cursor.getDouble(cursor.getColumnIndexOrThrow("deductions"));
            double netSalary = cursor.getDouble(cursor.getColumnIndexOrThrow("net_salary"));

            if (designation == null) designation = "";
            employee = new Employee(id, name, designation, totalDays, workedDays, salaryPerDay, allowances, deductions, netSalary);
        }
        cursor.close();
        return employee;
    }


    // Debug method to log all employees as string
    public String getAllEmployeesDebugString() {
        StringBuilder sb = new StringBuilder();
        List<Employee> employees = getAllEmployeesList();
        for (Employee emp : employees) {
            sb.append("empId: ").append(emp.getEmpId())
              .append(", name: ").append(emp.getName())
              .append(", designation: ").append(emp.getDesignation())
              .append(", totalDays: ").append(emp.getTotalDays())
              .append(", salaryPerDay: ").append(emp.getSalaryPerDay())
              .append(", workedDays: ").append(emp.getWorkedDays())
              .append(", allowances: ").append(emp.getAllowances())
              .append(", deductions: ").append(emp.getDeductions())
              .append(", netSalary: ").append(emp.getNetSalary())
              .append("\n");
        }
        return sb.toString();
    }

}
