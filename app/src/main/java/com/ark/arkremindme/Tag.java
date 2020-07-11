package com.ark.arkremindme;

import android.content.Context;

public class Tag {
    private int id;
    private String content;

    public Tag(Context context, int id, String content) {
        this.id = id;
        this.content = content;
    }
    public Tag(int id, String content){
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }
}
