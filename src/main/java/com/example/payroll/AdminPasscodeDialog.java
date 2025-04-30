package com.example.payroll;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminPasscodeDialog extends Dialog {

    private final String correctPasscode;
    private final PasscodeCallback callback;

    public interface PasscodeCallback {
        void onResult(boolean success);
    }

    public AdminPasscodeDialog(Context context, String correctPasscode, PasscodeCallback callback) {
        super(context);
        this.correctPasscode = correctPasscode;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_admin_passcode);

        EditText etPasscode = findViewById(R.id.etPasscode);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(v -> {
            String entered = etPasscode.getText().toString();
            if (correctPasscode.equals(entered)) {
                callback.onResult(true);
                dismiss();
            } else {
                Toast.makeText(getContext(), "Incorrect passcode", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            callback.onResult(false);
            dismiss();
        });
    }
}
