package com.ark.arkremindme;

import android.content.Context;
import android.content.SharedPreferences;

public class Show {

    private static final String PREFERENCES_NAMESPACE = "checkboxes_states";

    private int Id;
    private String Ten;
    boolean selected = false;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;

    public Show(Context context, int id, String ten, boolean selected) {
        Id = id;
        Ten = ten;
        mSettings = context.getSharedPreferences(PREFERENCES_NAMESPACE, 0);
        mEditor = mSettings.edit();
        setSelected(mSettings.getBoolean(Ten, selected));
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if(this.selected != selected) {
            mEditor.putBoolean(Ten, selected);
            mEditor.commit();
            this.selected = selected;
        }
    }
}
