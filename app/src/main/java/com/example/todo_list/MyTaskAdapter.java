package com.example.todo_list;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {
    public Context context;

    List<MyTaskList> lists;

    static List<MyTaskList> list;

    public static Parentfinish parent;


    public MyTaskAdapter(Context context, List<MyTaskList> myTaskLists,Parentfinish parent) {
        this.context = context;
        lists = myTaskLists;
        MyTaskAdapter.parent = parent;
        MyTaskAdapter.list = myTaskLists;

    }

    public interface Parentfinish
    {
        public void finishparemt();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView taskname,taskdesc,taskdate,tasktime,taskpriority,taskstatus;
        private RelativeLayout descpart,statuspart;
        public DatabaseHandler databaseHandler;
        public LocalData localData;


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
            localData = new LocalData(itemView.getContext());
            databaseHandler = new DatabaseHandler(itemView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), taskname.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), Task_Desc.class);
                    Cursor id = databaseHandler.getTaskId(localData.getsession("key_uname"),MyTaskAdapter.list.get(getAdapterPosition()).getTaskname(),list.get(getAdapterPosition()).getTaskdesc());
                    while (id.moveToNext())
                    {
                        intent.putExtra("tid",Integer.parseInt(id.getString(0)));
                        intent.putExtra("parentactivity",view.getContext().toString());
                        Log.d("Mohammed Saif 1 :", view.getContext().toString());
                        itemView.getContext().startActivity(intent);
                        MyTaskAdapter.parent.finishparemt();
                    }

                }
            });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {

//                    databaseHandler.deletetask(localData.getsession("key_uname"),getAdapterPosition()+1);
//
//                    return false;
//                }
//            });

        }





    }


    @Override
    public MyTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.tasklist,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskAdapter.ViewHolder holder, int position) {

        String desc;
        if(lists.get(position).getTaskdesc().length()>50)
        {
            desc = lists.get(position).getTaskdesc().substring(0,lists.get(position).getTaskdesc().length()-10)+"............";
        }
        else {
            desc = lists.get(position).getTaskdesc();
        }

        holder.itemView.setTag(lists.get(position));
        holder.taskname.setText(lists.get(position).getTaskname());
        holder.taskdesc.setText(desc);
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
