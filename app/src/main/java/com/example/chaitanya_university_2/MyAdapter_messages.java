package com.example.chaitanya_university_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter_messages extends RecyclerView.Adapter<MyAdapter_messages.MyViewHolder>
{
    Context c;
    String item_t[];
    String item_f_n[];
    String item_d[];
    String item_m[];

    public MyAdapter_messages(Context ct,String t[],String f_n[],String d[],String m[]) {
        c=ct;
        item_t=t;
        item_f_n=f_n;
        item_d=d;
        item_m=m;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.my_messages_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.m_t.setText(item_t[position]);
        holder.f_n.setText(item_f_n[position]+" on ");
        holder.d.setText(item_d[position]);
        holder.mss.setText(item_m[position]);

    }

    @Override
    public int getItemCount() {
        return item_t.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView m_t,f_n,d,mss;

      // RelativeLayout rel_lay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            m_t=itemView.findViewById(R.id.message_title);
            f_n=itemView.findViewById(R.id.text_faculty_name);
            d=itemView.findViewById(R.id.date_text);
            mss=itemView.findViewById(R.id.message_text);

          //  rel_lay=itemView.findViewById(R.id.list_layout);
        }
    }

}
