package com.ark.arkremindme;

public class NoiDung {
    private String TenND;
    private int id;

    public NoiDung(String tenND, int id) {
        TenND = tenND;
        this.id = id;
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
}
