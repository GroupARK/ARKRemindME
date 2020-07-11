package com.ark.arkremindme;

import android.content.Context;
import android.content.SharedPreferences;

public class GhiChu {

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";

    private int IdGC;
    private String TenGC;
    private String date;
    private String time;
    private String tag;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public GhiChu(Context context, int idGC, String tenGC, String date, String time, boolean selected, String tag) {
        IdGC = idGC;
        TenGC = tenGC;
        this.date = date;
        this.time = time;
        this.tag = tag;
        mSettings = context.getSharedPreferences(PREFERENCES_NAMESPACE, 0);
        mEditor = mSettings.edit();
        setSelected(mSettings.getBoolean(TenGC, selected));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIdGC() {
        return IdGC;
    }

    public void setIdGC(int idGC) {
        IdGC = idGC;
    }

    public String getTenGC() {
        return TenGC;
    }

    public void setTenGC(String tenGC) {
        TenGC = tenGC;
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
            mEditor.putBoolean(TenGC, selected);
            mEditor.commit();
            this.selected = selected;
        }
    }
}
