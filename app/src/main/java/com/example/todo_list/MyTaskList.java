package com.example.todo_list;

public class MyTaskList {
    private String taskname,taskdesc,taskdate,tasktime,taskpriority,taskstatus,taskcolor;

    public MyTaskList(String taskname, String taskdesc, String taskdate, String tasktime, String taskpriority, String taskstatus,String taskcolor) {
        this.taskname = taskname;
        this.taskdesc = taskdesc;
        this.taskdate = taskdate;
        this.tasktime = tasktime;
        this.taskpriority = taskpriority;
        this.taskstatus = taskstatus;
        this.taskcolor = taskcolor;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTaskdesc() {
        return taskdesc;
    }

    public void setTaskdesc(String taskdesc) {
        this.taskdesc = taskdesc;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public String getTaskpriority() {
        return taskpriority;
    }

    public void setTaskpriority(String taskpriority) {
        this.taskpriority = taskpriority;
    }

    public String getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(String taskstatus) {
        this.taskstatus = taskstatus;
    }
    public String getTaskcolor() {
        return taskcolor;
    }

    public void setTaskcolor(String taskname) {
        this.taskcolor = taskcolor;
    }


}
