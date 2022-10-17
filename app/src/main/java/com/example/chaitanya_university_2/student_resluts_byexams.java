package com.example.chaitanya_university_2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class student_resluts_byexams extends Fragment {


    RecyclerView rv_exams_results;

    int length;

    ProgressBar progressBar;

    public student_resluts_byexams() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_resluts_byexams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar=view.findViewById(R.id.circle_progress);

        rv_exams_results=view.findViewById(R.id.rv_exams);
        load_exam_timetable();
    }

    public void load_recyclerview()
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

        exams_timetable_adapter adapter=new exams_timetable_adapter(getContext(),dates_l,subject_l,time_l);
        rv_exams_results.setAdapter(adapter);
        // rv.setLayoutManager(new GridLayoutManager(this,3));
        rv_exams_results.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));

        progressBar.setVisibility(View.GONE);
    }

    public  String []dates_g=new String[10];
    public  String []subject_g=new String[10];
    public  String []time_g=new String[10];

    public static String []dates_l;
    public static String []subject_l;
    public static String []time_l;

    public void load_exam_timetable()
    {
        //final ProgressDialog progressDialog=new ProgressDialog(getContext());

      //  progressDialog.setMessage("Loading Please Wait...");
       // progressDialog.show();

        Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_results_byexams_EYY_DEPTCODE_G.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();

                        Toast.makeText(getContext(),"2",Toast.LENGTH_SHORT).show();
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

                                //  tv.append(dates_g[count]);
                                // tv.append(subject_g[count]);
                                //tv.append(time_g[count]);

                                //    tv.append(exams_g[count]+"||"+date_of_post_g[count]+"||"+exam_code_g[count]);
                                count++;
                            }
                            //     student_exams_results.d_l=count;
                            length=count+1;

                            dates_l=new String[count+1];
                            subject_l=new String[count+1];
                            time_l=new String[count+1];

                            load_recyclerview();

                           /* Intent intent=new Intent(getApplicationContext(),home_panel.class);

                            startActivity(intent);*/

                            Toast.makeText(getContext(),"Subjects Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //progressDialog.hide();
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();

                params.put("exam_code",Student_Details.student_selected_exam_code);
                params.put("table_name","results_"+Student_Details.student_batch_code+"_"+Student_Details.student_dept_code+"_"+Student_Details.student_section);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

}
