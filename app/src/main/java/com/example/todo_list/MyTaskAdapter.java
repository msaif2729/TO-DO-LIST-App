package com.example.todo_list;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ResourceBundle;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    Context context;

    List<MyTaskList> lists;

    public MyTaskAdapter(Context context, List<MyTaskList> myTaskLists) {
        this.context = context;
        lists = myTaskLists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView taskname,taskdesc,taskdate,tasktime,taskpriority,taskstatus;
        private RelativeLayout descpart,statuspart;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskname = itemView.findViewById(R.id.taskname);
            taskdesc = itemView.findViewById(R.id.taskdesc);
            taskdate = itemView.findViewById(R.id.taskdate);
            tasktime = itemView.findViewById(R.id.tasktime);
            taskpriority = itemView.findViewById(R.id.taskpriority);
            taskstatus = itemView.findViewById(R.id.taskstatus);
            descpart = itemView.findViewById(R.id.descpart);
            statuspart = itemView.findViewById(R.id.statuspart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), taskname.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), Task_Desc.class);
                    intent.putExtra("tdname",taskname.getText());
                    intent.putExtra("tddate",taskdate.getText());
                    intent.putExtra("tdtime",tasktime.getText());
                    intent.putExtra("tddesc",taskdesc.getText());
                    intent.putExtra("tdprio",taskpriority.getText());
                    intent.putExtra("tdstatus",taskstatus.getText());
                    itemView.getContext().startActivity(intent);
                }
            });


        }


    }

    @Override
    public MyTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.tasklist,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(lists.get(position));
        holder.taskname.setText(lists.get(position).getTaskname());
        holder.taskdesc.setText(lists.get(position).getTaskdesc());
        holder.taskpriority.setText(lists.get(position).getTaskpriority());
        holder.taskdate.setText(lists.get(position).getTaskdate());
        holder.tasktime.setText(lists.get(position).getTasktime());
        holder.taskstatus.setText(lists.get(position).getTaskstatus());
        holder.descpart.setBackgroundTintList(ColorStateList.valueOf(lists.get(position).getTaskcolor()));
        holder.statuspart.setBackgroundTintList(ColorStateList.valueOf(lists.get(position).getTaskcolor()));


    }



    @Override
    public int getItemCount() {
        return lists.size();
    }
}
