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


/**
 * A simple {@link Fragment} subclass.
 */
public class student_results_tab extends Fragment {


    RecyclerView rv_exams_results;

    String []result_name_g=new String[10];
    String []date_of_post_g=new String[10];
    String []exam_code_g=new String[10];

    String []result_name_l;
    String []date_of_post_l;
    String []exam_code_l;

    int length;

    TextView tv;

    ProgressBar progressBar;

    public student_results_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_results_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar=view.findViewById(R.id.circle_progress);

        rv_exams_results=view.findViewById(R.id.rv_exams);
        //tv=view.findViewById(R.id.tv);

        load_exams();

       // Toast.makeText(getContext(),"Rajesh",Toast.LENGTH_SHORT).show();

    }

    public void load_recyclerview()
    {
        for(int i=0;i<length;i++)
        {
            date_of_post_l[i]=date_of_post_g[i];
            result_name_l[i]=result_name_g[i];
            exam_code_l[i]=exam_code_g[i];
        }

        results_list_adapter adapter=new results_list_adapter(getContext(),date_of_post_l,result_name_l,exam_code_l);
        rv_exams_results.setAdapter(adapter);
        rv_exams_results.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        progressBar.setVisibility(View.GONE);
    }

    public void load_exams()
    {

        final ProgressDialog progressDialog=new ProgressDialog(getContext());

        //progressDialog.setMessage("Loading Please Wait...");
        //progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,"https://dorian-works.000webhostapp.com/get_results_list_EYY_DEPTCODE_G.php",
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
                                date_of_post_g[count]=JO.getString("date_of_post");

                                result_name_g[count]=JO.getString("result_name");

                                exam_code_g[count]=JO.getString("exam_code");

                               /* tv.append(exam_code_g[count]);
                                tv.append(date_of_post_g[count]);
                                tv.append(result_name_g[count]);*/
                                //    tv.append(exams_g[count]+"||"+date_of_post_g[count]+"||"+exam_code_g[count]);

                                count++;
                            }
                            length=count;

                            result_name_l=new String[count];
                            date_of_post_l=new String[count];
                            exam_code_l=new String[count];

                            load_recyclerview();
                           /* Intent intent=new Intent(getApplicationContext(),home_panel.class);

                            startActivity(intent);*/

                         //   Toast.makeText(getContext(),"Subjects Completed",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
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

                params.put("column_name","subject");
                params.put("table_name","results_list_"+Student_Details.student_batch_code+"_"+Student_Details.student_dept_code+"_"+Student_Details.student_section);

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}
