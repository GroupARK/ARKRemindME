package com.ark.arkremindme;

import android.content.Context;
import android.content.SharedPreferences;

public class CongViec {

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";

    private int IdCV;
    private String TenCV;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public CongViec(Context context, int idCV, String tenCV, boolean selected) {
        IdCV = idCV;
        TenCV = tenCV;
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
