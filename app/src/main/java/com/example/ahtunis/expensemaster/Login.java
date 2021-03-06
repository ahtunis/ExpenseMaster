package com.example.ahtunis.expensemaster;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

   // Context ctx = this;
    Button loginBtn;
    EditText userName;
    EditText password;
    String actualPass;
    String actualUser;
    String actualUserId;
    String actualBusinessId;
    int isAdmin;
    String firstName;


    String userNameTxt;
    String passwordTxt;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        // Check if permissions are available

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText password = (EditText) findViewById(R.id.userPassword);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

      loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

        if (userName != null && password != null) {
            userNameTxt = userName.getText().toString();
            passwordTxt = password.getText().toString();

            if (userNameTxt.isEmpty()) {
                userName.setError("A Username is required!");
            }

            if (passwordTxt.isEmpty()) {
                password.setError("A Password is required!");
            }

            //Test running on phone not emulator
           // startActivity(new Intent(Login.this, Selection.class));


            //Intent i = new Intent(getApplicationContext(), Selection.class);
            //i.putExtra("userName", userNameTxt);
            //i.putExtra("password", passwordTxt);
            //startActivity(i);


            // Runs in background thread !!!!!!!!!!!!!!!!!!!!
            new Background().execute(userNameTxt, passwordTxt);

        }
            }
        });
    }

    public void OnClickHere() {
        Toast toast = Toast.makeText(this, "onclikcHere", Toast.LENGTH_LONG);
        toast.show();
   }
//



    protected void Login() {
    }

    class Background extends AsyncTask<String, String, String> {
            String urlString;

        protected void onPreExecute(){

            urlString = "http://192.168.0.4/Expense/branches/atunis/app/login.app.php";

        }

        protected String onProgressUpdate(Void... params){

            return null;
        }



        @Override
        protected String doInBackground(String... params) {


            String username = params[0];
            String password = params[1];
            int tmp;
            String data = "";
            try{
                // Implement PHP Post methods for cheching login
                URL url = new URL(urlString);
                String urlParams = "userName=" + username + "&passWord=" + password;

                // implement SSL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                //connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                int status = connection.getResponseCode();
                InputStream is = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
//                while ((urlString = reader.readLine()) != null){
//                    builder.append(urlString);
//
//                }

                while((tmp=is.read()) != -1){
                    data+= (char) tmp;
                }
                // Close and disconnect
                reader.close();
                is.close();
                connection.disconnect();

                return data;
            }
            catch(Exception e) {
                e.printStackTrace();
                return "hello" + e.getMessage();
            }
        }

        //@Override
        protected void onPostExecute(String s){
            //String err = null;
            try{
                JSONObject root = new JSONObject(s);
                JSONObject userData = root.getJSONObject("user_data");

                actualPass = userData.getString("password");
                actualUser= userData.getString("userId");
                actualUserId= userData.getString("uid");
                actualBusinessId = userData.getString("businessId");
                isAdmin = userData.getInt("isAdmin");
                firstName = userData.getString("firstName");

            }
            catch(JSONException e ){
                e.printStackTrace();
                //err = "Exception" + e.getMessage();
            }

            // Make sure they are valid credentials

            if (actualUserId != null && actualPass != null && userNameTxt.equals(actualUserId)  && passwordTxt.equals(actualPass))
            {
                // Naviate to new page
                Intent mainPage = new Intent(Login.this, Selection.class);
                mainPage.putExtra("userName", actualUser);
                mainPage.putExtra("password", actualPass);
                mainPage.putExtra("userId", actualUserId);
                mainPage.putExtra("isAdmin", isAdmin);
                mainPage.putExtra("businessId", actualBusinessId);
                mainPage.putExtra("firstName", firstName);

                startActivity(mainPage);
            }
            else{
                 // Show invaid login attempt
                Toast toast = Toast.makeText(getApplicationContext(), "Invalid login credentials!", Toast.LENGTH_LONG);
                toast.show();

            }


        }
    }
}
