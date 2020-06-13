package com.ark.arkremindme;

import android.content.Context;
import android.content.SharedPreferences;

public class CongViec {

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";
    private int IdCV;
    private String TenCV;
    private String date;
    private String time;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public CongViec(Context context, int idCV, String tenCV, String date, String time, boolean selected) {
        IdCV = idCV;
        TenCV = tenCV;
        this.date = date;
        this.time = time;
        mSettings = context.getSharedPreferences(PREFERENCES_NAMESPACE, 0);
        mEditor = mSettings.edit();
        setSelected(mSettings.getBoolean(TenCV, selected));
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String tenCV) {
        TenCV = tenCV;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if(this.selected != selected) {
            mEditor.putBoolean(TenCV, selected);
            mEditor.commit();
            this.selected = selected;
        }
    }
}
