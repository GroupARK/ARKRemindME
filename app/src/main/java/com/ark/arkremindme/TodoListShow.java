package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListShow extends AppCompatActivity implements AppManager {

    Database showDatabase;

    ListView lvShow;
    ArrayList<Show> arrayShow;
    ShowAdapter adapter;
    String b= null;
    String delete = null;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_show);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        String title = getIntent().getExtras().getString("title");
        getSupportActionBar().setTitle(title);
        String a [] = title.split(" ");
        for(int i = 0 ; i < a.length; i++) {
            b += a[i];

    }
        title = getIntent().getExtras().getString("title");
        getSupportActionBar().setTitle(title);

        lvShow = (ListView) findViewById(R.id.listviewShow);
        arrayShow = new ArrayList<>();

        adapter = new ShowAdapter(this, R.layout.dong_noi_dung, arrayShow);
        lvShow.setAdapter(adapter);

        showDatabase = new Database(this, "database.sqlite", null, 1);
        showDatabase.QueryData("CREATE TABLE IF NOT EXISTS "+ b +"(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenShow VARCHAR(250))");
        delete = getIntent().getExtras().getString("delete");
        GetDataShow();
    }

    private void GetDataShow() {
        Cursor dataShow = showDatabase.GetData("SELECT * FROM " + b);
        arrayShow.clear();
        while (dataShow.moveToNext()) {
            String name = dataShow.getString(1);
            int id = dataShow.getInt(0);
            arrayShow.add(new Show(TodoListShow.this, id, name, false));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_noidung, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNoidung) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_noi_dung);

        final EditText edtND = (EditText) dialog.findViewById(R.id.edittextThemND);
        Button btnThemND = (Button) dialog.findViewById(R.id.buttonThemND);
        Button btnHuyND = (Button) dialog.findViewById(R.id.buttonHuyND);

        btnThemND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenND = edtND.getText().toString();
                if (tenND.equals("")) {
                    Toast.makeText(TodoListShow.this, "Vui lòng nhập tên nội dung", Toast.LENGTH_SHORT).show();
                } else {
                    showDatabase.QueryData("INSERT INTO "+ b + " VALUES(null,'"+ tenND +"')");
                    Toast.makeText(TodoListShow.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataShow();
                }
            }
        });

        btnHuyND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void DialogSuaCongViec(String name, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_noi_dung);
        dialog.show();

        final EditText edtNoiDung = (EditText) dialog.findViewById(R.id.edittextXNND);
        Button btnNoiDung = (Button) dialog.findViewById(R.id.buttonXNND);

        edtNoiDung.setText(name);
        btnNoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtNoiDung.getText().toString().trim();
                showDatabase.QueryData("UPDATE "+ b +" SET TenND = '"+ tenMoi +"' WHERE Id = '"+ id +"'");
                Toast.makeText(TodoListShow.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataShow();
            }
        });
    }

    @Override
    public void DialogXoaCV(String tencv, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa ghi chú "+ tencv +" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDatabase.QueryData("DELETE FROM "+ b +" WHERE Id = '"+ id +"'");
                Toast.makeText(TodoListShow.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataShow();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogXoa.show();

    }
    public void deleteTable(String b) {
        showDatabase.QueryData("Drop table " + b);
    }
}