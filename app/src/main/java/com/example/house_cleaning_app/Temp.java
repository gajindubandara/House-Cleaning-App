package com.example.house_cleaning_app;

public class Temp {
    private static String NIC;
    private static String jobID;
    private static String viewUserID;


    public static String getNIC() {
        return NIC;
    }

    public static void setNIC(String NIC) {
        Temp.NIC = NIC;
    }

    public static String getJobID() {
        return jobID;
    }

    public static void setJobID(String jobID) {
        Temp.jobID = jobID;
    }

    public static String getViewUserID() {
        return viewUserID;
    }

    public static void setViewUserID(String viewUserID) {
        Temp.viewUserID = viewUserID;
    }

}
