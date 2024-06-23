package com.example.todo_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowToast {

    public static void showToast(Context context,String mssg)
    {
        Toast toast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.toastdesign, null);
        ImageView toastImg = toastView.findViewById(R.id.toastimg);
        TextView toastTxt = toastView.findViewById(R.id.toasttxt);
        toastTxt.setText(mssg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }


}
