package com.andc.andcsocials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordDialog extends AppCompatDialogFragment {
    private TextInputLayout textField1, textField2;
    private EditText newPassword, newConfirmPassword;
    private ExtendedFloatingActionButton resetPasswordButton;
    private FloatingActionButton resetPasswordDialogCloseButton;

    private ResetPasswordDialog.ResetPasswordDialogListener resetPasswordDialogListener;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_reset_password,null);

        textField1=view.findViewById(R.id.TextField1);
        newPassword=view.findViewById(R.id.newPassword);
        textField2=view.findViewById(R.id.TextField2);
        newConfirmPassword=view.findViewById(R.id.newConfirmPassword);
        resetPasswordButton=view.findViewById(R.id.resetPasswordButton);
        resetPasswordDialogCloseButton=view.findViewById(R.id.resetPasswordDialogCloseButton);

        builder.setView(view);
        Dialog d=builder.create();
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPasswordText= newPassword.getText().toString();
                String newConfirmPasswordText= newConfirmPassword.getText().toString();

                if (newPasswordText.length()<6) {
                    textField1.setError("New Password must have atleast 6 characters!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else  {
                    textField1.setError(null);
                }

                if (!newConfirmPasswordText.equals(newPasswordText)) {
                    textField2.setError("Password does not match!");
                    textField2.setErrorIconDrawable(null);
                    return;
                }
                else  {
                    textField2.setError(null);
                }

                resetPasswordDialogListener.checkSignOutStatus(newPasswordText);
                d.dismiss();
            }
        });

        resetPasswordDialogCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        return d;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            resetPasswordDialogListener=(ResetPasswordDialog.ResetPasswordDialogListener)context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement ResetPasswordDialogListener!");
        }
    }

    public interface ResetPasswordDialogListener {
        void checkSignOutStatus(String newPassword);
    }
}
