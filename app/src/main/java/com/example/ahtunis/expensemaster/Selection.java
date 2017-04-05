package com.example.ahtunis.expensemaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Selection extends AppCompatActivity {
    String passwordString;
    String usernameString;
    String businessIdString;
    String userIdString;
    int isAdminString;

    TextView userIdTxt;


    Button uploadReceipt;
    Button uploadExpense;
    Button viewUploaded;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        uploadReceipt = (Button) findViewById(R.id.uploadIndividual);
        uploadExpense = (Button) findViewById(R.id.uploadExpense);
        viewUploaded = (Button) findViewById(R.id.viewUploaded);
        logOut = (Button) findViewById(R.id.logoutBtn);


        Intent i = getIntent();
        passwordString = i.getStringExtra("password");
        usernameString = i.getStringExtra("userName");
        businessIdString = i.getStringExtra("businessId");
        userIdString = i.getStringExtra("userId");
        isAdminString = i.getIntExtra("isAdmin", isAdminString);
        String firstName = i.getStringExtra("firstName");

        userIdTxt = (TextView) findViewById(R.id.helloSelection);

        userIdTxt.setText("Hello, " + firstName);


        uploadReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadPicture = new Intent(getApplicationContext(), UploadReceipt.class);
                uploadPicture.putExtra("userId", userIdString);
                startActivity(uploadPicture);
            }
        });


        uploadExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UploadExpense.class);
                i.putExtra("userId", usernameString);
                startActivity(i);
            }
        });


        viewUploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadedExpense = new Intent(getApplicationContext(), ViewExpenses.class);
                uploadedExpense.putExtra("userId", usernameString);
                startActivity(uploadedExpense);
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
