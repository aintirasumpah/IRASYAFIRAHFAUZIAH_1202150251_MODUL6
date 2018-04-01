package com.example.android.ira_1202150251_studycase6;

import com.google.firebase.database.IgnoreExtraProperties;

//Enkapsulasi data comment
@IgnoreExtraProperties
public class DBComm {
    String t1cmt, thecmt, cmtft;

    //Dibutuhkan kosong untuk membaca data
    public DBComm(){

    }

    //Constructor dari class ini
    public DBComm(String t1cmt, String thecmt, String cmtft) {
        this.t1cmt = t1cmt;
        this.thecmt = thecmt;
        this.cmtft = cmtft;
    }

    //Sisany getter untuk variabel dari class ini
    public String getCmtft() {
        return cmtft;
    }

    public String getT1cmt() {
        return t1cmt;
    }

    public String getThecmt() {
        return thecmt;
    }

}