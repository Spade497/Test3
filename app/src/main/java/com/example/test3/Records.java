package com.example.test3;

public class Records {
    private String user;
    private String status;
    private String time;

    Records() { }

    public Records(String user, String status, String time) {
        this.user = user;
        this.status = status;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
}
