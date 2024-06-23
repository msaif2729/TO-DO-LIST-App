package com.example.todo_list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LocalData  {

    private Context context;
    private SharedPreferences sharedPreferences;
    private final String PREF_MEMORY = "todo-app";
    private final int PREF_MODE = 0;
    private SharedPreferences.Editor editor;
    private final String KEY_LOGIN = "key_login";
    private final String KEY_NAME = "key_name";
    private final String KEY_UNAME = "key_uname";
    private final String KEY_PASS = "key_pass";


    public LocalData(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_MEMORY,PREF_MODE);
        editor = sharedPreferences.edit();
    }

    public boolean checksession()
    {
        if(sharedPreferences.contains(KEY_LOGIN))
        {
            return true;
        }
        else
            return false;
    }

    public void createsession(String name,String uname, String pass)
    {
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_UNAME,uname);
        editor.putString(KEY_PASS,pass);
        editor.putBoolean(KEY_LOGIN,true);
        editor.commit();
        editor.apply();
    }

    public void logout()
    {
        editor.remove(KEY_NAME);
        editor.remove(KEY_UNAME);
        editor.remove(KEY_PASS);
        editor.remove(KEY_LOGIN);
        editor.clear();
        editor.commit();
        editor.apply();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public String getsession(String key)
    {
        return sharedPreferences.getString(key,null);
    }

}
