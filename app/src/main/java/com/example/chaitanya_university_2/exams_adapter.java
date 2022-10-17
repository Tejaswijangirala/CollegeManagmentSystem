package com.example.chaitanya_university_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class exams_adapter extends RecyclerView.Adapter<exams_adapter.MyViewHolder>
{
    Context c;
    String item_exams[];
    String item_date[];
    String item_exam_code[];

    public exams_adapter(Context ct, String date[], String exams[], String ex_cd[]) {
        c=ct;
        item_exams=exams;
        item_date=date;
        item_exam_code=ex_cd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.rv_exams_results_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tv_exam.setText(item_exams[position]);
        holder.tv_date.setText(item_date[position]);


        holder.bt_timetable.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student_Details.student_exams_results_activity_title=item_exams[position];
                        Student_Details.student_selected_exam_code=item_exam_code[position];
                        Student_Details.student_selected_exams_or_results=1;

                       c.startActivity(student_drawer_panel.intent);

                      //  NavController navController= Navigation.findNavController(holder.itemView);

                       // navController.navigate(R.id.action_student_exams_results_tablayout_to_student_exam_timetable);

                       // Toast.makeText(c,item_exam_code[position],Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return item_exams.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_exam,tv_date;
        Button bt_timetable;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exam=(TextView) itemView.findViewById(R.id.exam_name);
            tv_date=(TextView) itemView.findViewById(R.id.date);
            bt_timetable=itemView.findViewById(R.id.exams_timetable);

            linearLayout=itemView.findViewById(R.id.exams_list);
        }
    }


}
