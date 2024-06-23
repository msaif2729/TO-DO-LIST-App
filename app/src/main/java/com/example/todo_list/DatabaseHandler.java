package com.example.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static String db = "todo.db";
    private static String tb1 = "user";
    private static String tb2 = "task";

    public DatabaseHandler(@Nullable Context context) {
        super(context,db,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+tb1+" (NAME TEXT, USERNAME TEXT PRIMARY KEY, PASSWORD TEXT) ");
        sqLiteDatabase.execSQL("CREATE TABLE " +tb2+" (TASKID INTEGER PRIMARY KEY AUTOINCREMENT,TASKUER TEXT, TASKNAME TEXT,TASKDESC TEXT,TASKDATE TEXT,TASKTIME TEXT,TASKPRIO TEXT,TASKSTAT TEXT,TASKCOLOR TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+tb1);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+tb2);
        onCreate(sqLiteDatabase);
    }

    public String adduser(String name,String uname,String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put("NAME", name);
        con.put("USERNAME", uname);
        con.put("PASSWORD", pass);
        long res = db.insert(tb1, null, con);
        if (res == -1)
        {
            return "NotInserted";
        }
        else{
            return "Inserted";
        }
    }

    public Cursor getuser(String uname, String pass)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT * FROM  "+tb1+" WHERE USERNAME= ? AND PASSWORD = ? ";
        Cursor res = db.rawQuery(query,new String[]{uname,pass});
        return res;
    }
    public Cursor getalluser()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT * FROM  "+tb1;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public String addtask(String tuser,String tname,String tdesc,String tdate, String ttime,String tprio,String  tstat,String tcolor,String tfav)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put("TASKUER",tuser);
        con.put("TASKNAME", tname);
        con.put("TASKDESC", tdesc);
        con.put("TASKDATE", tdate);
        con.put("TASKTIME", ttime);
        con.put("TASKPRIO", tprio);
        con.put("TASKSTAT", tstat);
        con.put("TASKCOLOR", tcolor);
        con.put("TASKFAV",tfav);

        long result = db.insert(tb2, null, con);
        if (result == -1) {
            return "failed";

        } else {
            return "successfully";

        }
    }

    public Cursor getusertask(String tuser,int position)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER= ? AND TASKID = ?";
        Cursor res = db.rawQuery(query,new String[]{tuser,String.valueOf(position)});
        return res;
    }
    public Cursor getalltask(String uname)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER=?";
        Cursor res = db.rawQuery(query,new String[] {uname});
        return res;
    }

    public String updatefav(String uname,int position,String fav)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASKFAV", fav);

        String whereClause = "TASKUER = ? AND TASKID = ?";
        String[] whereArgs = { uname, String.valueOf(position) };

        int rowsAffected = db.update(tb2, contentValues, whereClause, whereArgs);

        if (rowsAffected > 0) {
            return "successful";
        } else {
            return "failed";
        }
    }

    public Cursor showtodo(String uname)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER=? AND TASKSTAT=?";
        Cursor res = db.rawQuery(query,new String[]{uname,"To-Do"});
        return  res;
    }

    public Cursor showinprogress(String uname)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER=? AND TASKSTAT=?";
        Cursor res = db.rawQuery(query,new String[]{uname,"In-Progress"});
        return  res;
    }

    public Cursor showdone(String uname)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER=? AND TASKSTAT=?";
        Cursor res = db.rawQuery(query,new String[]{uname,"Done"});
        return  res;
    }

    public Cursor showfav(String uname)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = " SELECT * FROM  "+tb2+" WHERE TASKUER=? AND TASKFAV=?";
        Cursor res = db.rawQuery(query,new String[]{uname,"fav"});
        return  res;
    }

    public String deletetask(String uname,int position)
    {
        Log.d("DeleteTask", "Username: " + uname + ", Position: " + position);
        SQLiteDatabase db = getWritableDatabase();
        String whereclause = " TASKUER=? AND TASKID=? ";
        String[] whereselection = {uname,String.valueOf(position)};
        long res = db.delete(tb2,whereclause,whereselection);
        if(res!=-1)
        {
            return "deleted";
        }
        else {
            return "notdeleted";
        }
    }

    public String updatestatus(String uname,int position,String status)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASKSTAT", status);

        String whereClause = "TASKUER = ? AND TASKID = ?";
        String[] whereArgs = { uname, String.valueOf(position) };

        int rowsAffected = db.update(tb2, contentValues, whereClause, whereArgs);

        if (rowsAffected > 0) {
            return "successful";
        } else {
            return "failed";
        }
    }

    public Cursor getTaskId(String uname,String tname,String tdecs)
    {
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT TASKID FROM "+tb2+" WHERE TASKUER=? AND TASKNAME=? AND TASKDESC=? ";
        Cursor id = db.rawQuery(q,new String[]{uname,tname,tdecs});
        return id;
    }

    public String edittask(String uname,int position,String tname,String tdesc)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues con = new ContentValues();
        con.put("TASKNAME",tname);
        con.put("TASKDESC",tdesc);

        String whereclause = " TASKUER=? AND TASKID=? ";
        String[] whereselect = {uname, String.valueOf(position)};

        long res = db.update(tb2,con,whereclause,whereselect);
        if(res!=-1)
        {
            return "successful";
        }
        else {
            return "failed";
        }

    }

}
