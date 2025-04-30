package com.example.payroll.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.payroll.R;
import com.example.payroll.model.Employee;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private static final String TAG = "EmployeeAdapter";

    private Context context;
    private List<Employee> employeeList;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        if (holder.tvEmpId != null) {
            holder.tvEmpId.setText(employee.getEmpId());
        } else {
            Log.e(TAG, "tvEmpId is null at position " + position);
        }
        if (holder.tvName != null) {
            holder.tvName.setText(employee.getName());
        } else {
            Log.e(TAG, "tvName is null at position " + position);
        }
        if (holder.tvDesignation != null) {
            holder.tvDesignation.setText(employee.getDesignation());
        } else {
            Log.e(TAG, "tvDesignation is null at position " + position);
        }
        if (holder.tvTotalDays != null) {
            holder.tvTotalDays.setText(String.valueOf(employee.getTotalDays()));
        } else {
            Log.e(TAG, "tvTotalDays is null at position " + position);
        }
        if (holder.tvWorkedDays != null) {
            holder.tvWorkedDays.setText(String.valueOf(employee.getWorkedDays()));
        } else {
            Log.e(TAG, "tvWorkedDays is null at position " + position);
        }
        if (holder.tvSalaryPerDay != null) {
            holder.tvSalaryPerDay.setText(String.format("%.2f", employee.getSalaryPerDay()));
        } else {
            Log.e(TAG, "tvSalaryPerDay is null at position " + position);
        }
        if (holder.tvAllowances != null) {
            holder.tvAllowances.setText(String.format("%.2f", employee.getAllowances()));
        } else {
            Log.e(TAG, "tvAllowances is null at position " + position);
        }
        if (holder.tvDeductions != null) {
            holder.tvDeductions.setText(String.format("%.2f", employee.getDeductions()));
        } else {
            Log.e(TAG, "tvDeductions is null at position " + position);
        }
        if (holder.tvNetSalary != null) {
            holder.tvNetSalary.setText(String.format("%.2f", employee.getNetSalary()));
        } else {
            Log.e(TAG, "tvNetSalary is null at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmpId, tvName, tvDesignation, tvTotalDays, tvWorkedDays, tvSalaryPerDay, tvAllowances, tvDeductions, tvNetSalary;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpId = itemView.findViewById(R.id.tvEmpId);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            tvTotalDays = itemView.findViewById(R.id.tvTotalDays);
            tvWorkedDays = itemView.findViewById(R.id.tvWorkedDays);
            tvSalaryPerDay = itemView.findViewById(R.id.tvSalaryPerDay);
            tvAllowances = itemView.findViewById(R.id.tvAllowances);
            tvDeductions = itemView.findViewById(R.id.tvDeductions);
            tvNetSalary = itemView.findViewById(R.id.tvNetSalary);

            if (tvEmpId == null) Log.e(TAG, "tvEmpId is null in ViewHolder");
            if (tvName == null) Log.e(TAG, "tvName is null in ViewHolder");
            if (tvDesignation == null) Log.e(TAG, "tvDesignation is null in ViewHolder");
            if (tvTotalDays == null) Log.e(TAG, "tvTotalDays is null in ViewHolder");
            if (tvWorkedDays == null) Log.e(TAG, "tvWorkedDays is null in ViewHolder");
            if (tvSalaryPerDay == null) Log.e(TAG, "tvSalaryPerDay is null in ViewHolder");
            if (tvAllowances == null) Log.e(TAG, "tvAllowances is null in ViewHolder");
            if (tvDeductions == null) Log.e(TAG, "tvDeductions is null in ViewHolder");
            if (tvNetSalary == null) Log.e(TAG, "tvNetSalary is null in ViewHolder");
        }
    }
}
