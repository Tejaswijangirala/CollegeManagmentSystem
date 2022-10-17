package com.example.chaitanya_university_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class student_exams_results_activity extends AppCompatActivity {

    RecyclerView rv_exams_results;

    public  String []dates_g=new String[10];
    public  String []subject_g=new String[10];
    public  String []time_g=new String[10];

    public static String []dates_l;
    public static String []subject_l;
    public static String []time_l;

    int length;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exams_results_activity);

        progressBar=findViewById(R.id.circle_progress);

        rv_exams_results=findViewById(R.id.rv_exams);
        setTitle(Student_Details.student_exams_results_activity_title);

        if(Student_Details.student_selected_exams_or_results==1)
        {
            load_exam_timetable();
        }
        else if(Student_Details.student_selected_exams_or_results==2)
        {
            load_results_timetable();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void load_recyclerview_results()
    {
        dates_l[0]="Subject";
        subject_l[0]="Obtained Marks";
        time_l[0]="Total Marks";

        for(int i=0;i<length-1;i++)
        {
            dates_l[i+1]=dates_g[i];
            subject_l[i+1]=subject_g[i];
            time_l[i+1]=time_g[i];
        }

        exams_timetable_adapter adapter=new exams_timetable_adapter(this,dates_l,subject_l,time_l);
        rv_exams_results.setAdapter(adapter);
        // rv.setLayoutManager(new GridLayoutManager(this,3));
        rv_exams_results.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        progressBar.setVisibility(View.GONE);
    }

/*    public  String []dates_g=new String[10];
    public  String []subject_g=new String[10];
    public  String []time_g=new String[10];

    public static String []dates_l;
    public static String []subject_l;
    public static String []time_l;
*/
    public void load_results_timetable()
    {
       // final ProgressDialog progressDialog=new ProgressDialog(this);

        //progressDialog.setMessage("Loading Please Wait...");
        //progressDialog.show();

        //Toast.makeText(this,Student_Details.student_admno,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_results_byexams_EYY_DEPTCODE_G.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressDialog.dismiss();

                        //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray;

                            jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("server_response");
                            int count=0;

                            while(count<jsonArray.length())
                            {
                                JSONObject JO=jsonArray.getJSONObject(count);
                                dates_g[count]=JO.getString("subject");

                                subject_g[count]=JO.getString("obtained_marks");

                                time_g[count]=JO.getString("total_marks");

                               count++;
                            }

                            length=count+1;

                            dates_l=new String[count+1];
                            subject_l=new String[count+1];
                            time_l=new String[count+1];

                            load_recyclerview_results();

                            //Toast.makeText(getApplicationContext(),"Subjects Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                       // progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();

                params.put("exam_code",Student_Details.student_selected_exam_code);
                params.put("table_name","results_"+Student_Details.student_batch_code+"_"+Student_Details.student_dept_code+"_"+Student_Details.student_section);
                params.put("admno",Student_Details.student_admno);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void load_recyclerview_exams()
    {
        dates_l[0]="Date";
        subject_l[0]="Subject";
        time_l[0]="Timings";

        for(int i=0;i<length-1;i++)
        {
            dates_l[i+1]=dates_g[i];
            subject_l[i+1]=subject_g[i];
            time_l[i+1]=time_g[i];
        }

        exams_timetable_adapter adapter=new exams_timetable_adapter(this,dates_l,subject_l,time_l);
        rv_exams_results.setAdapter(adapter);
        rv_exams_results.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        progressBar.setVisibility(View.GONE);
    }

    public void load_exam_timetable()
    {
       // final ProgressDialog progressDialog=new ProgressDialog(this);

       // progressDialog.setMessage("Loading Please Wait...");
        //progressDialog.show();

        //Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_exams_timetable_DEPTCODE_Y.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  progressDialog.dismiss();

                        //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray;

                            jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("server_response");
                            int count=0;

                            while(count<jsonArray.length())
                            {
                                JSONObject JO=jsonArray.getJSONObject(count);
                                dates_g[count]=JO.getString("date");
                                subject_g[count]=JO.getString("subject");
                                time_g[count]=JO.getString("time");

                                count++;
                            }

                           length=count+1;

                            dates_l=new String[count+1];
                            subject_l=new String[count+1];
                            time_l=new String[count+1];

                            load_recyclerview_exams();

                          //  Toast.makeText(getApplicationContext(),"Subjects Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();

                params.put("exam_code",Student_Details.student_selected_exam_code);
                params.put("table_name","exams_timetable_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
