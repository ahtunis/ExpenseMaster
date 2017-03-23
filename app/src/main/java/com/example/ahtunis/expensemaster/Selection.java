package com.example.ahtunis.expensemaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Selection extends AppCompatActivity {
    String passwordString;
    String username;
    String businessId;
    String error;

    TextView userIdTxt;
    TextView usernameTxt;
    TextView password;
    TextView err;

    Button uploadIndividual;
    Button uploadByTrip;
    Button viewUploaded;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadIndividual = (Button) findViewById(R.id.uploadIndividual);
        uploadByTrip = (Button) findViewById(R.id.uploadTrip);
        viewUploaded = (Button) findViewById(R.id.viewUploaded);
        logOut = (Button) findViewById(R.id.logoutBtn);

        userIdTxt = (TextView) findViewById(R.id.userId);
        usernameTxt = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.password);
        err = (TextView) findViewById(R.id.error);


        Intent i = getIntent();
        passwordString = i.getStringExtra("password");
        username = i.getStringExtra("userName");
        //businessId = i.getStringExtra("businessId");
        //error = i.getStringExtra("error");
        userIdTxt.setText(passwordString);
        password.setText(username);

        // possibly used saved instance
//        if (savedInstanceState != null){
//            savedInstanceState.putString(userId, userId);
//            savedInstanceState.putString(username, username);
//            savedInstanceState.putString(businessId, businessId);
//
//        }

        if (savedInstanceState != null){
            String userNameTxt = savedInstanceState.getString("userName");
            String passwordTxt = savedInstanceState.getString("password");

            userIdTxt.setText(userNameTxt);
            usernameTxt.setText(passwordTxt);

        }

        // Main if information was passed

       // businessIdTxt.setText(businessId);
        //err.setText(error);

        uploadIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadIndividualExpense.class));
            }
        });


        uploadByTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TripUpload.class));
            }
        });


        viewUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewExpenses.class));
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }


}
