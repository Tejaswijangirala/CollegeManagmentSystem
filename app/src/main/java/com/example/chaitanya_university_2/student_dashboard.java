package com.example.chaitanya_university_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * A simple {@link Fragment} subclass.
 */
public class student_dashboard extends Fragment {

    TextView student_name_tv,student_admno_tv,student_department_tv,todays_date_tv,message_titletv;

    RecyclerView rv,rv_messages;

    public student_dashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv_no_classes=view.findViewById(R.id.no_classes);

        if(Student_Details.count_of_subjects_today!=0)
        {
            iv_no_classes.setVisibility(View.GONE);
        }

        String time2[]=new String[Student_Details.count_of_subjects_today];
        String subjects2[]=new String[Student_Details.count_of_subjects_today];

        for(int i=0;i<Student_Details.count_of_subjects_today;i++)      //copying loaded timings and subjects to another arrays
        {   time2[i]=Student_Details.time1[i];
            subjects2[i]=Student_Details.subjects1[i];}

        student_name_tv= view.findViewById(R.id.student_name);
        student_name_tv.setText(Student_Details.student_name);

        student_admno_tv=view.findViewById(R.id.student_admno);
        student_admno_tv.setText(Student_Details.student_admno);

        student_department_tv=view.findViewById(R.id.student_department);
        student_department_tv.setText(Student_Details.student_department);

        todays_date_tv=view.findViewById(R.id.today_date_tv);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE , MMM dd, yyyy");
        String Date=simpleDateFormat.format(calendar.getTime());
        todays_date_tv.setText(Date);

        message_titletv=view.findViewById(R.id.message_title); //use for checking
        // message_titletv.setText(Student_Details.student_dept_code+"_"+Student_Details.student_year);

        rv=view.findViewById(R.id.recyclerview);
        final MyAdapter adapter=new MyAdapter(getContext(),time2,subjects2);
        rv.setAdapter(adapter);
        // rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));

        //set student messages

        String title[]={"Assignment","Extra Class","Material"};
        String faculty_name[]={"Ravi Kumar","Devi","Raju"};
        String date[]={"12 sep,2020","16 July,2020","19 Aug,2020"};
        String messages[]={"Dear student write any 2 questions as assignment Dear student write any 2 questions as assignment Dear student write any 2 questions as assignment Dear student write any 2 questions as assignment Dear student write any 2 questions as assignment Dear student write any 2 questions as assignment Dear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignmentDear student write any 2 questions as assignment","Tomorrow at 12 PM you will have class","Read this material for your mid exams"};

        rv_messages=view.findViewById(R.id.messages_recyclerview);
        final MyAdapter_messages adapter_messages=new MyAdapter_messages(getContext(),title,faculty_name,date,messages);
        rv_messages.setAdapter(adapter_messages);

        final LinearLayoutManager lm=new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        rv_messages.setLayoutManager(lm);

        final Button b2,b1;

        b2=view.findViewById(R.id.prevoius_b);
        b1=view.findViewById(R.id.next_b);

        b2.setEnabled(false);
        b1.setEnabled(false);

        if(adapter_messages.getItemCount()!=0)
        {
            b1.setEnabled(true);
        }

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lm.findLastCompletelyVisibleItemPosition() > 0){
                    lm.scrollToPosition(lm.findLastCompletelyVisibleItemPosition() - 1);
                    b1.setEnabled(true);
                }
                if(lm.findLastCompletelyVisibleItemPosition()==1)
                {
                    b2.setEnabled(false);
                }
            }
        });

        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lm.findLastCompletelyVisibleItemPosition() < (adapter_messages.getItemCount() - 1)){
                            lm.scrollToPosition(lm.findLastCompletelyVisibleItemPosition() + 1);
                            b2.setEnabled(true);
                        }
                        if(lm.findLastCompletelyVisibleItemPosition()==(adapter_messages.getItemCount()-2))
                        {
                            b1.setEnabled(false);
                        }
                    }
                }
        );

    }


}
