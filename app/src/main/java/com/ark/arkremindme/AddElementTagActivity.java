package com.ark.arkremindme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddElementTagActivity extends AppCompatActivity{
    Database database;
    ListView listNote, listTodolist, listTask;
    ArrayList<GhiChu> arrayListNote;
    ArrayList<CongViec> arrayListTodolist;
    ArrayList<ViecLam> arrayListTask;
    String title;
    AddElementAdapter adapter1;
    AddElementTodoListAdapter adapter2;
    AddElementTaskAdapter adapter3;
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

        adapter1 = new AddElementAdapter(this, R.layout.dong_add_element,arrayListNote);
        listNote.setAdapter(adapter1);
        database = new Database(this, "database.sqlite", null, 1);

        adapter2 = new AddElementTodoListAdapter(this, R.layout.dong_add_element, arrayListTodolist);
        listTodolist.setAdapter(adapter2);

        adapter3 = new AddElementTaskAdapter(this, R.layout.dong_add_element, arrayListTask);
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
                arrayListTask.add(new ViecLam(this, name, id, false, tag));
            } else {
                String [] a = tag.split("/");
                int b = exist(a, title);
                if(b == 0) {
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
                arrayListTodolist.add(new CongViec(this,id,name,todayDate,currentTime,false,tag));
            } else {
                String [] a = tag.split("/");
                int b = exist(a, title);
                if(b == 0) {
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
                arrayListNote.add(new GhiChu(this, id, name, todayDate, currentTime, false, tag));
            } else {
                String []a = tag.split("/");
                int b = exist(a, title);
                if(b == 0) {
                    arrayListNote.add(new GhiChu(this, id, name, todayDate, currentTime, false, tag));
                }
            }
        }
        adapter1.notifyDataSetChanged();
    }
    public void addElementTag(int id, int type){
            if(type == 1) {
                Cursor dataGhiChu = database.GetData("Select * from GhiChu2 Where Id = '" + id + "'");
                while (dataGhiChu.moveToNext()) {
                    String tag = dataGhiChu.getString(4);
                    database.QueryData("UPDATE GhiChu2 SET Tag = '"+ tag +"/"+ title + "' WHERE Id = '" + id + "'");
                    Toast.makeText(this, "Note này đã được thêm vào tag " + title, Toast.LENGTH_SHORT).show();
                    arrayListNote.clear();
                    GetdataNote();
                }
            }
            if( type == 2) {
                Cursor dataCongViec = database.GetData("Select * from CongViec1 Where Id = '" + id + "'");
                while (dataCongViec.moveToNext()) {
                    String tag = dataCongViec.getString(4);
                    database.QueryData("UPDATE CongViec1 SET Tag = '"+ tag +"/"+ title + "' WHERE Id = '" + id + "'");
                    Toast.makeText(this, "TodoList này đã được thêm vào tag " + title, Toast.LENGTH_SHORT).show();
                    arrayListTodolist.clear();
                    GetDataTodolist();
                }
            }
            if(type == 3) {
                Cursor dataViecLam = database.GetData("Select * from ViecLam1 Where Id = '" + id + "'");
                while (dataViecLam.moveToNext()) {
                    String tag = dataViecLam.getString(2);
                    database.QueryData("UPDATE ViecLam1 SET Tag = '"+ tag +"/"+ title + "' WHERE Id = '" + id + "'");
                    Toast.makeText(this, "Task này đã được thêm vào tag " + title, Toast.LENGTH_SHORT).show();
                    arrayListTask.clear();
                    GetDataTask();
                }
            }
    }
}
