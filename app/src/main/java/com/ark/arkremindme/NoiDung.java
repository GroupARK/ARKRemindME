package com.ark.arkremindme;

import android.content.Context;
import android.content.SharedPreferences;

public class NoiDung {

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";

    private String TenND;
    private int id;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public NoiDung(Context context, String tenND, int id, boolean selected) {
        TenND = tenND;
        this.id = id;
        mSettings = context.getSharedPreferences(PREFERENCES_NAMESPACE, 0);
        mEditor = mSettings.edit();
        setSelected(mSettings.getBoolean(TenND, selected));
    }

    public String getTenND() {
        return TenND;
    }

    public void setTenND(String tenND) {
        TenND = tenND;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if(this.selected != selected) {
            mEditor.putBoolean(TenND, selected);
            mEditor.commit();
            this.selected = selected;
        }
    }
}
