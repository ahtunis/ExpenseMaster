package com.example.ahtunis.expensemaster;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UploadExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    EditText date;
    EditText total;
    EditText business;
    EditText comment;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        business = (EditText) findViewById(R.id.businessName);
        total = (EditText) findViewById(R.id.expenseTotal);
        date = (EditText) findViewById(R.id.expenseDate);
        comment = (EditText) findViewById(R.id.expenseComment);

        Button save = (Button) findViewById(R.id.saveBtn);
        Button saveAndContinue = (Button) findViewById(R.id.saveAndContinue);
        Button clear = (Button) findViewById(R.id.clearBtn);
        Intent i = getIntent();

        userId = i.getStringExtra("userId");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(v);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearTextFields();
            }
        });

        saveAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save and new up the page
                Expense e = ValidateFieldsAreNotNull();

                if(e.IsValidForSave()){
                    SaveExpense(e);
                    Intent i = new Intent(getApplicationContext(), UploadExpense.class);
                    i.putExtra("userId", userId);

                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please complete all fields prior to saving.", Toast.LENGTH_LONG).show();
                }


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Expense e = ValidateFieldsAreNotNull();
                // Save and redirect to view expensepage

                if(e.IsValidForSave()){
                    SaveExpense(e);
                    Intent i = new Intent(getApplicationContext(), ViewExpenses.class);
                    i.putExtra("userId", userId);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please complete all fields prior to saving.", Toast.LENGTH_LONG).show();
                }




            }
        });

    }

    private Expense ValidateFieldsAreNotNull() {
        String businessTxt = business.getText().toString();
        String totalAmount = total.getText().toString();
        String expenseDate = date.getText().toString();
        String expenseComment = comment.getText().toString();

        return new Expense(businessTxt, expenseDate, totalAmount, expenseComment);

    }

    private void SaveExpense(Expense e){


        // DO logic for upload expense to DB Here

    }

    private void ClearTextFields() {
        business.setText("");
        total.setText("");
        date.setText("");
        comment.setText("");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void datePicker(View v){

        DatePickerFragment f = new DatePickerFragment();
        FragmentManager fm = getFragmentManager();
        f.show(fm,"date");
    }

    public void setDate( final Calendar calendar){

       final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        setDate(c);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static  class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle save){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return  new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                    getActivity(), year, month, day );


        }
    }
}
