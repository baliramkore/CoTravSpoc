package com.cotrav.cotravspoc.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.cotrav.cotravspoc.R;
import com.google.android.material.textfield.TextInputEditText;

public class OTPFragmentDialog extends AppCompatDialogFragment
{

    TextInputEditText editTextOTP;
    Button txtYes,txtNo;
    TextView btnResend;
    private OTPDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.verify_otp_dialog,null);
        builder.setView(view).setTitle("").setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {




            }
        });

        editTextOTP=(TextInputEditText)view.findViewById(R.id.edt_text_otp);
        txtYes=(Button) view.findViewById(R.id.yes_txt);
        txtNo=(Button) view.findViewById(R.id.no_txt);


        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editOTP=editTextOTP.getText().toString();
                listener.checkOTP(editOTP);
                dismiss();

            }
        });
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OTPDialogListener) context;
        }catch (ClassCastException e)
        {

            throw new ClassCastException(context.toString()+
                    "must implement OTPDialogListener");
        }
    }

    public interface OTPDialogListener
    {

        void checkOTP(String otp);

    }
}
