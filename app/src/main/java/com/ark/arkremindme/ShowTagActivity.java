package com.ark.arkremindme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowTagActivity extends AppCompatActivity{
    Database database;
    ListView listNote, listTodolist, listTask;
    ArrayList<GhiChu> arrayListNote;
    ArrayList<CongViec> arrayListTodolist;
    ArrayList<ViecLam> arrayListTask;
    String title;
    ShowTagNoteAdapter adapter1;
    ShowTagTodoListAdapter adapter2;
    ShowTagTaskAdapter adapter3;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element_tag);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        title = getIntent().getExtras().getString("title");
        getSupportActionBar().setTitle(title);
        listNote = findViewById(R.id.listViewNoteInTag);
        listTodolist = findViewById(R.id.listViewToDoListInTag);
        listTask = findViewById(R.id.listviewTaskInTag);
        arrayListNote = new ArrayList<>();
        arrayListTodolist = new ArrayList<>();
        arrayListTask = new ArrayList<>();
        adapter1 = new ShowTagNoteAdapter(this, R.layout.dong_add_element,arrayListNote);
        listNote.setAdapter(adapter1);
        database = new Database(this, "database.sqlite", null, 1);

        adapter2 = new ShowTagTodoListAdapter(this, R.layout.dong_add_element, arrayListTodolist);
        listTodolist.setAdapter(adapter2);

        adapter3 = new ShowTagTaskAdapter(this, R.layout.dong_add_element, arrayListTask);
        listTask.setAdapter(adapter3);

        GetdataNote();
        GetDataTodolist();
        GetDataTask();
    }
    private int exist(String a[], String b) {
        for(String i: a){
            if(i.equals(b)) {
                return 1;
            }
        }
        return 0;
    }

    private void GetDataTask() {
        Cursor dataTask = database.GetData("SELECT * FROM ViecLam1");
        arrayListTask.clear();
        while (dataTask.moveToNext()) {
            String name = dataTask.getString(1);
            int id = dataTask.getInt(0);
            String tag = dataTask.getString(2);
            if(tag.equals("")){
            } else {
                String [] a = tag.split("/");
                int b = exist(a, title);
                if(b == 1) {
                    arrayListTask.add(new ViecLam(this, name, id, false, tag));
                }
            }
        }
        adapter3.notifyDataSetChanged();
    }

    private void GetDataTodolist() {
        Cursor dataTodolist = database.GetData("SELECT * FROM CongViec1");
        arrayListTodolist.clear();
        while (dataTodolist.moveToNext()) {
            String name = dataTodolist.getString(1);
            int id = dataTodolist.getInt(0);
            String todayDate = dataTodolist.getString(2);
            String currentTime = dataTodolist.getString(3);
            String tag = dataTodolist.getString(4);
            if(tag.equals("")){
            } else {
                String [] a = tag.split("/");
                int b = exist(a, title);
                if(b == 1) {
                    arrayListTodolist.add(new CongViec(this,id,name,todayDate,currentTime,false,tag));
                }
            }
        }
        adapter2.notifyDataSetChanged();
    }

    private void GetdataNote() {
        Cursor dataGhiChu = database.GetData("SELECT * FROM GhiChu2");
        arrayListNote.clear();
        while (dataGhiChu.moveToNext()) {
            String name = dataGhiChu.getString(1);
            int id = dataGhiChu.getInt(0);
            String todayDate = dataGhiChu.getString(2);
            String currentTime = dataGhiChu.getString(3);
            String tag = dataGhiChu.getString(4);
            if(tag.equals("")) {
            } else {
                String []a = tag.split("/");
                int b = exist(a, title);
                if(b == 1) {
                    arrayListNote.add(new GhiChu(this, id, name, todayDate, currentTime, false, tag));
                }
            }
        }
        adapter1.notifyDataSetChanged();
    }

    public void deleteElementTag(int id, int type) {
        if(type == 1) {

        }Cursor dataGhiChu = database.GetData("Select * from GhiChu2 Where Id = '" + id + "'");
        while (dataGhiChu.moveToNext()) {
            String tag = dataGhiChu.getString(4);
            String newtag = tag.replace("/"+title,"");
            database.QueryData("UPDATE GhiChu2 SET Tag = '"+ newtag + "' WHERE Id = '" + id + "'");
            arrayListNote.clear();
            GetdataNote();
        }
        if( type == 2) {
            Cursor dataCongViec = database.GetData("Select * from CongViec1 Where Id = '" + id + "'");
            while (dataCongViec.moveToNext()) {
                String tag = dataCongViec.getString(4);
                String newtag = tag.replace("/"+title,"");
                database.QueryData("UPDATE CongViec1 SET Tag = '"+ newtag + "' WHERE Id = '" + id + "'");
                arrayListTodolist.clear();
                GetDataTodolist();
            }
        }
        if(type == 3) {
            Cursor dataViecLam = database.GetData("Select * from ViecLam1 Where Id = '" + id + "'");
            while (dataViecLam.moveToNext()) {
                String tag = dataViecLam.getString(2);
                String newtag = tag.replace("/"+title,"");
                database.QueryData("UPDATE ViecLam1 SET Tag = '"+ newtag + "' WHERE Id = '" + id + "'");
                arrayListTask.clear();
                GetDataTask();
            }
        }
    }
}
