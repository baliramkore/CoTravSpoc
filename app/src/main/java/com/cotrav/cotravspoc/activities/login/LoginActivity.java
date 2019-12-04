package com.cotrav.cotravspoc.activities.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cotrav.cotravspoc.R;
import com.cotrav.cotravspoc.activities.SignUpActivity;
import com.cotrav.cotravspoc.activities.contactus.ContactUsActivity;
import com.cotrav.cotravspoc.activities.home.HomeActivity;
import com.cotrav.cotravspoc.fragments.OTPFragmentDialog;
import com.cotrav.cotravspoc.viewmodels.UserViewModel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements OTPFragmentDialog.OTPDialogListener {


    EditText emalIdEditText, passwordEdditText,textOTP;
    static String otp_str,textviewOtp;
    private static String TAG = "LoginActivity";
    ImageView loginBtn,imageOTPVerify;
    Button btnContactUs;
    UserViewModel userViewModel;
    ProgressBar progressBar;
    Context context;
    TextView txtResend,erroText;
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ProgressDialog progressDialog;
    TextView btnSignup;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignup=findViewById(R.id.txt_signup);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(LoginActivity.this);
        context=LoginActivity.this;
        emalIdEditText = findViewById(R.id.email_id);
        passwordEdditText = findViewById(R.id.password);
        loginBtn = (ImageView) findViewById(R.id.signin);
        imageOTPVerify = (ImageView) findViewById(R.id.otp_verify);
        btnContactUs=(Button) findViewById(R.id.contact_us);
        linearLayout=(LinearLayout)findViewById(R.id.lay_otp_valid);
        textOTP=(EditText)findViewById(R.id.textview_otp);
        txtResend=(TextView)findViewById(R.id.textview_resend);
        erroText=(TextView)findViewById(R.id.textview_error);
        emalIdEditText.setBackgroundColor(Color.TRANSPARENT);
        passwordEdditText.setBackgroundColor(Color.TRANSPARENT);


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (userViewModel.isLogin()) {
            Log.d(TAG, "access_token " + userViewModel.getAccessToken().getValue() + "  " + Boolean.toString(userViewModel.isLogin()));

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();

        } else {
            Log.d(TAG, "LOGIN ACTIVITY STARTED");

            userViewModel.getLoginSuccessful().observe(this, new Observer<String>()
                    {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if (s.equals("Successful")){
                                progressBar.setVisibility(View.GONE);

                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                LoginActivity.this.finish();
                            }
                            if (s.equals("Otp sent Successfully")){
                                progressBar.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.INVISIBLE);
                                imageOTPVerify.setVisibility(View.VISIBLE);
                                Toasty.success(LoginActivity.this,"Otp sent successfully").show();
                                emalIdEditText.setFocusable(false);
                                passwordEdditText.setFocusable(false);

                                progressBar.setVisibility(View.VISIBLE);
                                otp_verificationDialog();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();

                            }
                        }
                    });


        }





        userViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (s.equals("")) {
                    Toast.makeText(LoginActivity.this, s + "", Toast.LENGTH_SHORT).show();
                } else {


                    final Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.custom_dialog_box);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    TextView mDialogTitile = dialog.findViewById(R.id.dialog_title);
                    TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);

                    mDialogTitile.setText("Wrong Details");
                    mDialogmsg.setText("Please Check Your Credentials");

                    Button myes = dialog.findViewById(R.id.yes_txt);
                    Button mNo = dialog.findViewById(R.id.no_txt);

                    myes.setText("Yes");
                    myes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            dialog.dismiss();
                        }
                    });
                    mNo.setText("Cancel");
                    mNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();


                }

                Toast.makeText(LoginActivity.this, s + "", Toast.LENGTH_SHORT).show();

            }
        });



        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ContactUsActivity.class));
             }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emalIdEditText.getText().toString().equals("")) {

                    emalIdEditText.setError("Enter Email ID");
                    YoYo.with(Techniques.Shake).duration(200).repeat(1).playOn(emalIdEditText);
                }
                if (passwordEdditText.getText().toString().equals("")) {
                    passwordEdditText.setError("Enter Password");
                    YoYo.with(Techniques.Shake).duration(200).repeat(1).playOn(passwordEdditText);
                }
                if (emalIdEditText.getText().toString().length() > 1 && passwordEdditText.getText().toString().length() > 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressDialog.setMessage("Checking Credentials");
                    progressDialog.show();
                    userViewModel.performLogin(emalIdEditText.getText().toString(), passwordEdditText.getText().toString());
                    progressBar.setVisibility(View.GONE);

                }



            }
        });
        imageOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   if(textOTP.getText().length()==6)
                   {

                       userViewModel.verifyCode(textOTP.getText().toString());

                   }else
                       {
                           Toast.makeText(LoginActivity.this,"PLease Check OTP",Toast.LENGTH_SHORT).show();

                       }







            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                userViewModel.performLogin(emalIdEditText.getText().toString(),passwordEdditText.getText().toString());
                progressBar.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }



public void otp_verificationDialog()
{

    OTPFragmentDialog otpFragmentDialog=new OTPFragmentDialog();
    otpFragmentDialog.show(getSupportFragmentManager(),"otp dialog");



}


    @Override
    public void checkOTP(String otp) {

        otp_str= String.valueOf(userViewModel.getAccessOTP().getValue());
        //textviewOtp=otp;
        textOTP.setText(otp);
        if (otp.equals(otp_str)){
            if (textOTP.getText().toString().length()==6){
                Toast.makeText(LoginActivity.this, "verifing", Toast.LENGTH_SHORT).show();
                userViewModel.verifyCode(textOTP.getText().toString());
            }
            else{
                Toast.makeText(LoginActivity.this, "Check Otp", Toast.LENGTH_SHORT).show();
                textOTP.setError("check 6 digit OTP");
            }
        }else
            {
                // loginBtn.setImageResource(R.drawable.icon_correct);

                erroText.setText("Please Enter Valiad OTP");
                erroText.setTextColor(this.getResources().getColor(R.color.cotravRed));
                erroText.setVisibility(View.VISIBLE);
                txtResend.setVisibility(View.VISIBLE);
                txtResend.setTextColor(this.getResources().getColor(R.color.colorPrimary));




            }
 ;


    }
}
