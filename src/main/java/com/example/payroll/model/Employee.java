package com.example.payroll.model;

public class Employee {
    private String empId;
    private String name;
    private String designation;
    private int totalDays;
    private int workedDays;
    private double salaryPerDay;
    private double allowances;
    private double deductions;
    private double netSalary;

    public Employee(String empId, String name, String designation, int totalDays, int workedDays,
                    double salaryPerDay, double allowances, double deductions, double netSalary) {
        this.empId = empId;
        this.name = name;
        this.designation = designation;
        this.totalDays = totalDays;
        this.workedDays = workedDays;
        this.salaryPerDay = salaryPerDay;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getWorkedDays() {
        return workedDays;
    }

    public double getSalaryPerDay() {
        return salaryPerDay;
    }

    public double getAllowances() {
        return allowances;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetSalary() {
        return netSalary;
    }
}
