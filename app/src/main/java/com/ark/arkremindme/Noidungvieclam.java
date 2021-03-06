package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Noidungvieclam extends AppCompatActivity implements AppManager {

    Database noidungDatabase;

    ListView lvNoiDung;
    ArrayList<NoiDung> arrayNoiDung;
    NoiDungAdapter adapter;
    String b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noidungvieclam);
        String title = getIntent().getExtras().getString("title");
        String a [] = title.split(" ");
        for(int i = 0 ; i< a.length; i++){
            b += a[i];
        }
        getSupportActionBar().setTitle(title);


        lvNoiDung = (ListView) findViewById(R.id.listviewND);
        arrayNoiDung = new ArrayList<>();

        adapter = new NoiDungAdapter(this, R.layout.dong_noi_dung, arrayNoiDung);
        lvNoiDung.setAdapter(adapter);

        noidungDatabase = new Database(this, "noidung.sqlite", null, 1);

        noidungDatabase.QueryData("CREATE TABLE IF NOT EXISTS "+ b +"(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenND VARCHAR(250))");

        noidungDatabase.QueryData("CREATE TABLE IF NOT EXISTS "+ b+"title(TenND VARCHAR(250))");

        final EditText edt = (EditText) findViewById(R.id.edittextsave);
        Button btnSave = (Button) findViewById(R.id.button2);
        String name1 = getNote();
        edt.setText(name1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt.getText().toString();
                noidungDatabase.QueryData("Insert into "+ b +"title"+" values('"+name+"')");
            }
        });

        GetDataNoiDung();
    }


    private void GetDataNoiDung() {
        Cursor dataViecLam = noidungDatabase.GetData("SELECT * FROM "+ b);
        arrayNoiDung.clear();
        while (dataViecLam.moveToNext()) {
            String name = dataViecLam.getString(1);
            int id = dataViecLam.getInt(0);
            arrayNoiDung.add(new NoiDung(Noidungvieclam.this, name, id, false));
        }
        adapter.notifyDataSetChanged();
    }
    public String getNote() {
        Cursor dataNote = noidungDatabase.GetData("SELECT * FROM " + b + "title");
        String name = null;
        while (dataNote.moveToNext()) {
            name = dataNote.getString(0);
        }
        return name;
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
                    Toast.makeText(Noidungvieclam.this, "Vui lòng nhập tên nội dung", Toast.LENGTH_SHORT).show();
                } else {
                    noidungDatabase.QueryData("INSERT INTO "+ b + " VALUES(null,'"+ tenND +"')");
                    Toast.makeText(Noidungvieclam.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataNoiDung();
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
                noidungDatabase.QueryData("UPDATE "+b+" SET TenND = '"+ tenMoi +"' WHERE Id = '"+ id +"'");
                Toast.makeText(Noidungvieclam.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataNoiDung();
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
                noidungDatabase.QueryData("DELETE FROM "+b+" WHERE Id = '"+ id +"'");
                Toast.makeText(Noidungvieclam.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataNoiDung();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogXoa.show();
    }
}
