package com.cotrav.cotravspoc.activities.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cotrav.cotravspoc.R;

public class ProfileActivity extends AppCompatActivity {

    TextView txtspocId,txtspocName,txtspocComapany,txtspocContact,txtspocEmail;
    SharedPreferences loginpref;
    Toolbar mtoolbar;
    String spocId,spocName,spocCompany,spocContact,spocEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mtoolbar=findViewById(R.id.toolbar);
        mtoolbar=(Toolbar)findViewById(R.id.toolbar);
        mtoolbar.setTitle("Profile Page");
        mtoolbar.setNavigationIcon(R.drawable.test_back);

        mtoolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        loginpref = getSharedPreferences("login_info", MODE_PRIVATE);
        txtspocId=(TextView)findViewById(R.id.txt_spoc_id);
        txtspocName=(TextView)findViewById(R.id.txt_spoc_name);
        txtspocComapany=(TextView)findViewById(R.id.txt_comapany_name);
        txtspocContact=(TextView)findViewById(R.id.txt_spoc_contact);
        txtspocEmail=(TextView)findViewById(R.id.txt_spoc_email);

        setdata();
    }

    private void setdata() {

        spocId= loginpref.getString("user_id", "n");
        spocName=loginpref.getString("user_name","n");
        spocCompany=loginpref.getString("user_name","n");
        spocContact=loginpref.getString("user_contact","n");
        spocEmail=loginpref.getString("email","n");

        txtspocId.setText(spocId);
        txtspocName.setText(spocName);
        txtspocComapany.setText(spocCompany);
        txtspocContact.setText(spocContact);
        txtspocEmail.setText(spocEmail);


    }


}
