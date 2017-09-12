package com.aharoldk.burnyourcalories.model;

public class Home {
    double total_calories;
    int total_walk_off;
    int total_run_off;
    String date_activity;

    public Home() {
    }

    public Home(double total_calories, int total_walk_off, int total_run_off, String date_activity) {
        this.total_calories = total_calories;
        this.total_walk_off = total_walk_off;
        this.total_run_off = total_run_off;
        this.date_activity = date_activity;
    }

    public double getTotal_calories() {
        return total_calories;
    }

    public void setTotal_calories(double total_calories) {
        this.total_calories = total_calories;
    }

    public int getTotal_walk_off() {
        return total_walk_off;
    }

    public void setTotal_walk_off(int total_walk_off) {
        this.total_walk_off = total_walk_off;
    }

    public int getTotal_run_off() {
        return total_run_off;
    }

    public void setTotal_run_off(int total_run_off) {
        this.total_run_off = total_run_off;
    }

    public String getDate_activity() {
        return date_activity;
    }

    public void setDate_activity(String date_activity) {
        this.date_activity = date_activity;
    }
}
