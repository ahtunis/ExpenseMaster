package com.example.ahtunis.expensemaster;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewExpenses extends AppCompatActivity {


    ListView expenseList;
    Expense[] list;
    List<Expense> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);
        expenseList = (ListView) findViewById(R.id.expenseList);
        Intent i = getIntent();
        String userId = i.getStringExtra("userId");

        new Background().execute(userId);

        //Expense expesnse = new Expense(1, "test", 1, 10.00);

        //list = new Expense[]{expesnse, new Expense(2, "test", 2, 10.00)};

    }

    class  Background extends AsyncTask<String, String, String>{
        String urlString;

        protected void onPreExecute(){

            urlString = "http://192.168.0.4/Expense/branches/atunis/app/getExpenseList.app.php";

        }

        @Override
        protected String doInBackground(String... params) {


            String userid = params[0];

            int tmp;
            String data = "";
            try{
                // Implement PHP Post methods for cheching login
                URL url = new URL(urlString);
                String urlParams = "userId=" + userid ;

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
                JSONArray userData = root.getJSONArray("expense_list");

                //E public Expense(String business, String date, String total, String status, String expenseType) {


                arrayList = new ArrayList<Expense>();

                for(int i=0; i< userData.length(); i++) {
                    JSONObject obj = userData.getJSONObject(i);

                    arrayList.add(new Expense(obj.getString("businessName"),
                            obj.getString("expenseDate"),
                            obj.getString("total"),
                            obj.getString("expenseStatus"),
                            obj.getString("expenseType"))
                    );
                }


               // list = new List<Expense>();

                BindExpenseList(arrayList);



            }
            catch(JSONException e ){
                e.printStackTrace();
                //err = "Exception" + e.getMessage();
            }

            // Make sure they are valid credentials

        }
    }

    private void BindExpenseList(List<Expense> arrayList) {

        // Build Adapter
        CustomAdapter adapter  = new CustomAdapter();

        // Configure list
        expenseList.setAdapter(adapter);
    }


    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            int coutn = arrayList.size();
            return coutn;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.expense_layout, null);

            TextView business = (TextView) convertView.findViewById(R.id.businessName);
            TextView amount = (TextView) convertView.findViewById(R.id.expenseTotal);
            TextView dateView = (TextView) convertView.findViewById(R.id.expenseDate);
            TextView expenseType = (TextView) convertView.findViewById(R.id.expenseType);
            TextView expenseStatus = (TextView) convertView.findViewById(R.id.expenseStatus);


            String businessName = arrayList.get(i).getBusiness();
            String total = arrayList.get(i).getTotal();
            String date = arrayList.get(i).getDate();
            String type = arrayList.get(i).getStatus();
            String status = arrayList.get(i).getStatus();

            business.setText(businessName);
            amount.setText(total);
            dateView.setText(date);
            expenseType.setText(type);
            expenseStatus.setText(status);

            return convertView;

        }
    }

    public void onBackBtn(View v){

        startActivity(new Intent(this, Selection.class));
    }
}
