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

public class UpdatePhoneNumberDialog extends AppCompatDialogFragment {
    private TextInputLayout textField1;
    private EditText newPhoneNumber;
    private ExtendedFloatingActionButton updatePhoneNumberButton;
    private FloatingActionButton updatePhoneNumberDialogCloseButton;

    private UpdatePhoneNumberDialog.UpdatePhoneNumberDialogListener updatePhoneNumberDialogListener;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_update_phone_number,null);

        textField1=view.findViewById(R.id.TextField1);
        newPhoneNumber=view.findViewById(R.id.newPhoneNumber);
        updatePhoneNumberButton=view.findViewById(R.id.updatePhoneNumberButton);
        updatePhoneNumberDialogCloseButton=view.findViewById(R.id.updatePhoneNumberDialogCloseButton);

        builder.setView(view);
        Dialog d=builder.create();
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        updatePhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhoneNumberText= newPhoneNumber.getText().toString();
                if (newPhoneNumberText.length()<1) {
                    textField1.setError("Enter New Phone Number!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else  {
                    textField1.setError(null);
                }

                updatePhoneNumberDialogListener.checkSignOutStatus(Long.parseLong("91"+newPhoneNumberText));
                d.dismiss();
            }
        });

        updatePhoneNumberDialogCloseButton.setOnClickListener(new View.OnClickListener() {
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
            updatePhoneNumberDialogListener=(UpdatePhoneNumberDialog.UpdatePhoneNumberDialogListener)context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement UpdatePhoneNumberDialogListener!");
        }
    }

    public interface UpdatePhoneNumberDialogListener {
        void checkSignOutStatus(long newPhone);
    }
}
