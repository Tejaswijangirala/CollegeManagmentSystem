package com.example.chaitanya_university_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText student_admno;
    EditText student_password;

    Button student_login_b;

    ProgressDialog progressDialog;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Login Progress...");

        progressBar=findViewById(R.id.circle_progress);
        progressBar.setVisibility(View.GONE);

        student_admno= findViewById(R.id.student_admno);
        student_password= findViewById(R.id.student_password);

        student_login_b= findViewById(R.id.student_login_button);

        student_login_b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        student_login_b.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        check_details();
    }

    public void check_details()
    {
        String str_username=String.valueOf(student_admno.getText());
        String str_password=String.valueOf(student_password.getText());

        if(str_username.equals("")||str_password.equals(""))
        {
            Toast.makeText(this,"Fields Missing",Toast.LENGTH_SHORT).show();
            student_login_b.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            login_function(str_username,str_password);
            student_admno.setText("");
            student_password.setText("");
        }
    }

    public void login_function(final String str_username, final String str_password)
    {
        final String table_name=str_username.substring(0,3);

       // progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://dorian-works.000webhostapp.com//Chaitanya_University_Student_login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject=new JSONObject(response);

                            String error_value=jsonObject.getString("error");

                            if(error_value.equals("true"))
                            {
                                Student_Details.student_batch_code=table_name;
                                Student_Details.student_admno=str_username;
                                Student_Details.student_name=jsonObject.getString("name");
                                Student_Details.student_department=jsonObject.getString("department");
                                Student_Details.student_phone=jsonObject.getString("phone");
                                Student_Details.student_year=jsonObject.getString("year");
                                Student_Details.student_dept_code=jsonObject.getString("dept_code");
                                Student_Details.student_section=jsonObject.getString("section");

                                load_timings();
                                load_subjects();

                            }
                            else
                            {
                               // progressDialog.dismiss();
                                student_login_b.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                           // progressDialog.dismiss();
                            student_login_b.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.hide();
                student_login_b.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("table_name",table_name);
                params.put("admno",str_username);
                params.put("phone",str_password);

                return  params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    int all_cou=0;

    private void load_timings()
    {

        //final ProgressDialog progressDialog=new ProgressDialog(this);
       // progressDialog.setMessage("Loading Please Wait1...");
        //progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_times_subjects.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();

                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray;

                            jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("server_response");
                            int count=0;

                            while(count<jsonArray.length())
                            {
                                JSONObject JO=jsonArray.getJSONObject(count);
                                Student_Details.time1[count]=JO.getString("questions");

                                count++;
                            }
                            all_cou=count;
                            Student_Details.count_of_subjects_today=count;

                            //Toast.makeText(getApplicationContext(),"Times Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            //progressDialog.dismiss();
                            student_login_b.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"No Toast times",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.hide();
                        //progressDialog.dismiss();
                        student_login_b.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();

                params.put("day",get_today());
                params.put("column_name","time");
                params.put("table_name","timetable_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(stringRequest);

    }

    private void load_subjects()
    {

        //final ProgressDialog progressDialog=new ProgressDialog(this);
        //progressDialog.setMessage("Loading Please Wait2...");
        //progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_times_subjects.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();

                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray;

                            jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("server_response");
                            int count=0;

                            while(count<jsonArray.length())
                            {
                                JSONObject JO=jsonArray.getJSONObject(count);
                                Student_Details.subjects1[count]=JO.getString("questions");

                                count++;
                            }

                            Intent intent=new Intent(getApplicationContext(),student_drawer_panel.class);
                            student_login_b.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            //progressDialog.dismiss();
                            startActivity(intent);

                           // Toast.makeText(getApplicationContext(),"Subjects Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            //progressDialog.dismiss();
                            student_login_b.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        //progressDialog.hide();
                        student_login_b.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();

                params.put("day",get_today());
                params.put("column_name","subject");
                params.put("table_name","timetable_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(stringRequest);
    }

    private String get_today()
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("EEEE");  //for full form
        String day_full_form=simpleDateFormat2.format(calendar.getTime());

        return day_full_form;
    }
}

