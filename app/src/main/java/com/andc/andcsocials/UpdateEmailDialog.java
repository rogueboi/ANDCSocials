package com.andc.andcsocials;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmailDialog extends AppCompatDialogFragment {

    private TextInputLayout textField1;
    private EditText newEmail;
    private ExtendedFloatingActionButton updateEmailButton;
    private FloatingActionButton updateEmailDialogCloseButton;

    private UpdateEmailDialogListener updateEmailDialogListener;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_update_email,null);

        textField1=view.findViewById(R.id.TextField1);
        newEmail=view.findViewById(R.id.newEmail);
        updateEmailButton=view.findViewById(R.id.updateEmailButton);
        updateEmailDialogCloseButton=view.findViewById(R.id.updateEmailDialogCloseButton);

        builder.setView(view);
        Dialog d=builder.create();
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        updateEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmailText= newEmail.getText().toString();
                if (newEmailText.length()<1) {
                    textField1.setError("Enter New Email Address!");
                    textField1.setErrorIconDrawable(null);
                    return;
                }
                else  {
                    textField1.setError(null);
                }

                updateEmailDialogListener.checkSignOutStatus(newEmailText);
                d.dismiss();
            }
        });

        updateEmailDialogCloseButton.setOnClickListener(new View.OnClickListener() {
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
            updateEmailDialogListener=(UpdateEmailDialogListener)context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must Implement UpdateEmailDialogListener!");
        }
    }

    public interface UpdateEmailDialogListener {
        void checkSignOutStatus(String newEmail);
    }
}
