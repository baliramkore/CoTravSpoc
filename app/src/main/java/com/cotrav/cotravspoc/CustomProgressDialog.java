package com.cotrav.cotravspoc;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

public class CustomProgressDialog
{
    public static Dialog showProgressDialog(Context ctx) {

        Dialog dialog = new Dialog(ctx, R.style.Progres_Custom_Dialog);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams WMLP = dialog.getWindow().getAttributes();
        WMLP.dimAmount = (float) 0.4;

        return dialog;
    }
}