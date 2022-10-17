package com.example.chaitanya_university_2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class student_timetable extends Fragment {

    RecyclerView rv_monday,rv_tuesday,rv_wednesday,rv_thursday,rv_friday,rv_saturday,rv_sunday;

    String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

     RecyclerView []rv_days=new RecyclerView[7];

    int []days_status=new int[7];

    String [][]times=new String[7][10];
    String [][]subjects=new String[7][10];

    int all_cou=0,loaded_status=0;
    TextView tv_all;
    ImageView iv_no_clss;

   ProgressDialog progressDialog;

   ProgressBar progressBar;

    public student_timetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();

        return inflater.inflate(R.layout.fragment_student_timetable, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //progressBar=view.findViewById(R.id.circle_progress);

        rv_days[0]=view.findViewById(R.id.rv_monday);
        rv_days[1]=view.findViewById(R.id.rv_tuesday);
        rv_days[2]=view.findViewById(R.id.rv_wednesday);
        rv_days[3]=view.findViewById(R.id.rv_thursday);
        rv_days[4]=view.findViewById(R.id.rv_friday);
        rv_days[5]=view.findViewById(R.id.rv_saturday);
        rv_days[6]=view.findViewById(R.id.rv_sunday);

        load_full_timetable_timings();
        load_full_timetable_subjects();

        super.onViewCreated(view, savedInstanceState);
    }

    public void copy_details_to_other_array()
    {
        String arr_times[];
        String arr_subjects[];


        for(int i=0;i<7;i++)
        {
            arr_times=new String[days_status[i]];
            arr_subjects=new String[days_status[i]];

            for(int j=0;j<days_status[i];j++)
            {
                arr_times[j]=times[i][j];
                arr_subjects[j]=times[i][j];
                //    tv_all.append(arr_times[j]+"||");
            }
            String str_iv="no_classes_"+String.valueOf(i);
            int id=getResources().getIdentifier(str_iv,"id",getContext().getPackageName());
            ImageView imageView=getView().findViewById(id);

            if(days_status[i]!=0)
            {
                imageView.setVisibility(View.GONE);
                MyAdapter adapter = new MyAdapter(getContext(), arr_times, arr_subjects);
                rv_days[i].setAdapter(adapter);
                // rv.setLayoutManager(new GridLayoutManager(this,3));
                rv_days[i].setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
            }
        }

       // progressBar.setVisibility(View.GONE);
        progressDialog.dismiss();

    }

    public void load_full_timetable_timings()
    {
       RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest = null;

        //progressDialog.setMessage("Loading Please Wait1...");
        //progressDialog.show();

        for(int i=0;i<7;i++) {
            final int finalI = i;
            //progressDialog.setMessage("Loading Please Wait..."+i);
            //progressDialog.show();

            stringRequest = new StringRequest(Request.Method.POST, "https://dorian-works.000webhostapp.com/get_times_subjects.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // progressDialog.dismiss();

                            try {
                                JSONObject jsonObject;
                                JSONArray jsonArray;

                                int count = 0;
                                jsonObject = new JSONObject(response);
                                jsonArray = jsonObject.getJSONArray("server_response");


                                while (count < jsonArray.length()) {
                                    JSONObject JO = jsonArray.getJSONObject(count);
                                    times[finalI][count] = JO.getString("questions");

                                    count++;
                                }
                                days_status[finalI]=count;

                                all_cou = count;

                                //   Student_Details.count_of_subjects_today = count;

                                //   Toast.makeText(getApplicationContext(), "Times Completed", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                //progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "No Toast", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                           // progressDialog.hide();
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("day", days[finalI]);
                    params.put("column_name", "time");
                    params.put("table_name", "timetable_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section );
                  //  params.put("table_name", "timetable_CSE_1" );

                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }

    }

    public void load_full_timetable_subjects()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        //final ProgressDialog progressDialog=new ProgressDialog(getContext());
        StringRequest stringRequest = null;

       // progressDialog.setMessage("Loading Please Wait1...");
        //progressDialog.show();

        for(int i=0;i<7;i++) {

            final int finalI = i;
          //  progressDialog.setMessage("Loading Please Wait..."+i);
           // progressDialog.show();
            stringRequest = new StringRequest(Request.Method.POST, "https://dorian-works.000webhostapp.com/get_times_subjects.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // progressDialog.dismiss();

                            try {
                                JSONObject jsonObject;
                                JSONArray jsonArray;

                                int count = 0;
                                jsonObject = new JSONObject(response);
                                jsonArray = jsonObject.getJSONArray("server_response");

                                while (count < jsonArray.length()) {
                                    JSONObject JO = jsonArray.getJSONObject(count);
                                    subjects[finalI][count] = JO.getString("questions");

                                    count++;
                                }
                                //   days_status[finalI]=count;

                                all_cou = count;
                                if (finalI==6)
                                {
                                    copy_details_to_other_array();
                                }

                                //   Student_Details.count_of_subjects_today = count;
                                //   Toast.makeText(getApplicationContext(), "Times Completed", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                if (finalI==6)
                                {
                                    copy_details_to_other_array();
                                }
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "No Toast", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            //progressDialog.hide();
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("day", days[finalI]);
                    params.put("column_name", "subject");
                    params.put("table_name", "timetable_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section );
                   // params.put("table_name", "timetable_CSE_1" );

                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }


    }
}
