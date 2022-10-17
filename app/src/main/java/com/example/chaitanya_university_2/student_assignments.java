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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class student_assignments extends Fragment {

    String subject_name[]=new String[25];
    String ass_no[]=new String[25];
    String ass_description[]=new String[25];
    String ann_date[]=new String[25];
    String due_date[]=new String[25];

    int global_count;

    String loc_subject_name[];
    String loc_ass_no[];
    String loc_ass_description[];
    String loc_ann_date[];
    String loc_due_date[];

    String err;

    RecyclerView rv;

    ProgressBar progressBar;

    ProgressDialog progressDialog;

    public student_assignments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_student_assignments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //progressDialog=new ProgressDialog(getContext());
        //progressDialog.setMessage("Loading Please Wait...");
        progressBar=view.findViewById(R.id.circle_progress);


        rv=view.findViewById(R.id.rv_assignments_list);
        load_assignments();
    }

    public void load_assignments()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());

        StringRequest stringRequest = null;
        //progressDialog.show();
        stringRequest = new StringRequest(Request.Method.POST, "https://dorian-works.000webhostapp.com/get_assignments.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject;
                            JSONArray jsonArray;

                            jsonObject=new JSONObject(response);
                            jsonArray=jsonObject.getJSONArray("server_response");
                            int count=0;

                          //  Toast.makeText(getContext(), "Started", Toast.LENGTH_SHORT).show();

                            while(count<jsonArray.length())
                            {
                               // Toast.makeText(getContext(), "1 Loaded...", Toast.LENGTH_SHORT).show();
                                JSONObject JO=jsonArray.getJSONObject(count);
                                subject_name[count]=JO.getString("subject");
                                ass_no[count]="Assignment - "+JO.getString("assignment_no");
                                ass_description[count]=JO.getString("assignment_description");
                                ann_date[count]=JO.getString("ann_date");
                                due_date[count]=JO.getString("due_date");
                                count++;
                            }

                            global_count=count;
                            load_assignment_adapter();
                            //progressDialog.dismiss();

                        } catch (Exception e) {
                           // progressDialog.show();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "No Assignments", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.hide();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("table_name","assignments_"+Student_Details.student_dept_code+"_"+Student_Details.student_year+"_"+Student_Details.student_section);

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void load_assignment_adapter()
    {
        copying_to_array();


        assignment_adapter adapter=new assignment_adapter(getContext(),loc_subject_name,loc_ass_no,loc_ass_description,loc_ann_date,loc_due_date);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        progressBar.setVisibility(View.GONE);
    }

    public void copying_to_array()
    {
        loc_subject_name=new String[global_count];
        loc_ass_no=new String[global_count];
        loc_ass_description=new String[global_count];
        loc_ann_date=new String[global_count];
        loc_due_date=new String[global_count];

        for(int i=0;i<global_count;i++)
        {
            loc_subject_name[i]=subject_name[i];
            loc_ass_no[i]=ass_no[i];
            loc_ass_description[i]=ass_description[i];
            loc_ann_date[i]=ann_date[i];
            loc_due_date[i]=due_date[i];
        }
    }

}
