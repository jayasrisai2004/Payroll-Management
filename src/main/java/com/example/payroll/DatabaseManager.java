package com.example.payroll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "appdata.db";
    private static final int DB_VERSION = 1;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_USERNAME = "username";
    public static final String COL_USER_PASSWORD = "password";

    // Employees table
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COL_EMP_ID = "emp_id";
    public static final String COL_EMP_NAME = "name";
    public static final String COL_EMP_DESIGNATION = "designation";
    public static final String COL_EMP_TOTAL_DAYS = "total_days";
    public static final String COL_EMP_SALARY_PER_DAY = "salary_per_day";
    public static final String COL_EMP_WORKED_DAYS = "worked_days";
    public static final String COL_EMP_ALLOWANCES = "allowances";
    public static final String COL_EMP_DEDUCTIONS = "deductions";
    public static final String COL_EMP_NET_SALARY = "net_salary";

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COL_USER_USERNAME + " TEXT PRIMARY KEY, " +
                COL_USER_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        String createEmployeesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_EMPLOYEES + " (" +
                COL_EMP_ID + " TEXT PRIMARY KEY, " +
                COL_EMP_NAME + " TEXT, " +
                COL_EMP_DESIGNATION + " TEXT, " +
                COL_EMP_TOTAL_DAYS + " INTEGER, " +
                COL_EMP_SALARY_PER_DAY + " REAL, " +
                COL_EMP_WORKED_DAYS + " INTEGER, " +
                COL_EMP_ALLOWANCES + " REAL, " +
                COL_EMP_DEDUCTIONS + " REAL, " +
                COL_EMP_NET_SALARY + " REAL)";
        db.execSQL(createEmployeesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);
    }

    // User methods
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_USERNAME, username);
        cv.put(COL_USER_PASSWORD, password);
        long res = db.insert(TABLE_USERS, null, cv);
        return res != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_USERNAME + "=? AND " + COL_USER_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Employee methods
    public boolean insertEmployee(String empId, String name, String designation, int totalDays,
                                  double salaryPerDay, int workedDays, double allowances,
                                  double deductions, double netSalary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_EMP_ID, empId);
        cv.put(COL_EMP_NAME, name);
        cv.put(COL_EMP_DESIGNATION, designation);
        cv.put(COL_EMP_TOTAL_DAYS, totalDays);
        cv.put(COL_EMP_SALARY_PER_DAY, salaryPerDay);
        cv.put(COL_EMP_WORKED_DAYS, workedDays);
        cv.put(COL_EMP_ALLOWANCES, allowances);
        cv.put(COL_EMP_DEDUCTIONS, deductions);
        cv.put(COL_EMP_NET_SALARY, netSalary);
        long res = db.insert(TABLE_EMPLOYEES, null, cv);
        return res != -1;
    }

    public Cursor getEmployeeById(String empId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " + COL_EMP_ID + "=?", new String[]{empId});
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_USER_USERNAME + " FROM " + TABLE_USERS, null);
    }
}
