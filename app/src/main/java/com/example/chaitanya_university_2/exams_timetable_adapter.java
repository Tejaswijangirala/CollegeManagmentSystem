package com.example.chaitanya_university_2;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class exams_timetable_adapter extends RecyclerView.Adapter<exams_timetable_adapter.MyViewHolder>
{
    Context c;
    String item_subject[];
    String item_date[];
    String item_time[];

    public exams_timetable_adapter(Context ct, String date[], String subject[], String time[]) {
        c=ct;
        item_subject=subject;
        item_date=date;
        item_time=time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.rv_exams_timetable,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_subject.setText(item_subject[position]);
        holder.tv_date.setText(item_date[position]);
        holder.tv_time.setText(item_time[position]);

        if(position==0)
        {
            holder.tv_subject.setTypeface(null,Typeface.BOLD);
            holder.tv_date.setTypeface(null,Typeface.BOLD);
            holder.tv_time.setTypeface(null,Typeface.BOLD);

            holder.tv_subject.setTextColor(Color.BLACK);
            holder.tv_date.setTextColor(Color.BLACK);
            holder.tv_time.setTextColor(Color.BLACK);
        }

         if (position==item_subject.length-1)
         {
             holder.tv_date.setBackgroundResource(R.drawable.bottom_left);
             holder.tv_subject.setBackgroundResource(R.drawable.bottom_middle);
             holder.tv_time.setBackgroundResource(R.drawable.bottom_right);
         }
         else
         {
             holder.tv_date.setBackgroundResource(R.drawable.left_cell);
             holder.tv_subject.setBackgroundResource(R.drawable.top_middle);
             holder.tv_time.setBackgroundResource(R.drawable.right_cell);
         }

    }

    @Override
    public int getItemCount() {
        return item_subject.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_subject,tv_date,tv_time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subject=(TextView) itemView.findViewById(R.id.subject_tv);
            tv_date=(TextView) itemView.findViewById(R.id.date_tv);
            tv_time=itemView.findViewById(R.id.time_tv);


        }
    }





}
