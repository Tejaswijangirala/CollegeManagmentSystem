package com.example.chaitanya_university_2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class assignment_adapter extends RecyclerView.Adapter<assignment_adapter.MyViewHolder>
{
    Context c;
    String item_s_n[];
    String item_a_n[];
    String item_a_d[];
    String item_ann_date[];
    String item_due_date[];

    private int mExpandedPosition = -1;
    int previousExpandedPosition=-1;

    public assignment_adapter(Context ct, String s_n[], String a_n[], String a_d[], String ann_dat[], String due_dat[]) {
        c=ct;
        item_s_n=s_n;
        item_a_n=a_n;
        item_a_d=a_d;
        item_ann_date=ann_dat;
        item_due_date=due_dat;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(c);
        View view=inflater.inflate(R.layout.assignment_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.s_n.setText(item_s_n[position]);
        holder.a_n.setText(item_a_n[position]);
        holder.a_d.setText(item_a_d[position]);
        holder.annm_dat.setText(item_ann_date[position]);
        holder.due_dat.setText(item_due_date[position]);


        final boolean isExpanded=position==mExpandedPosition;

        holder.llExpandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.ib.setActivated(isExpanded);

        if(isExpanded)
        { previousExpandedPosition=position;
            holder.ib.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            //Toast.makeText(c,"Position "+position+"is expanded",Toast.LENGTH_SHORT).show();
        }
        else
        {   holder.ib.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            //Toast.makeText(c,"No position is expanded",Toast.LENGTH_SHORT).show();
        }

        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition=isExpanded?-1:position;


                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_s_n.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView s_n,a_n,a_d,annm_dat,due_dat;
        ImageView ib;
        LinearLayout llExpandArea;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            s_n=itemView.findViewById(R.id.subject_name);
            a_n=itemView.findViewById(R.id.assignment_no);
            a_d=itemView.findViewById(R.id.description);
            annm_dat=itemView.findViewById(R.id.assm_date);
            due_dat=itemView.findViewById(R.id.due_date);


            llExpandArea=itemView.findViewById(R.id.expandable_layout);
            ib=itemView.findViewById(R.id.down_button);


        }
    }

}
