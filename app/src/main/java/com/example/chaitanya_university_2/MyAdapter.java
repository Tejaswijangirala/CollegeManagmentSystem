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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context c;
    String item_n[];
    String item_d[];

    public MyAdapter(Context ct,String n[],String d[]) {
        c=ct;
        item_n=n;
        item_d=d;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.layout_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.n.setText(item_n[position]);
        holder.d.setText(item_d[position]);

    }

    @Override
    public int getItemCount() {
        return item_n.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView n,d;
        RelativeLayout rel_lay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            n=(TextView) itemView.findViewById(R.id.item_title);
            d=(TextView) itemView.findViewById(R.id.description);
            rel_lay=itemView.findViewById(R.id.list_layout);
        }
    }

}
