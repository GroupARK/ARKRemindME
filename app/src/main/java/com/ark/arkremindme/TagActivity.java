package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity implements AppManager {

    Database noteDatabase;

    ListView listTag;
    ArrayList<Tag> arrayTag;
    TagAdapter adapter;
    @SuppressLint("RestrictedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag2);
        getSupportActionBar().setTitle("List Tag");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        listTag = (ListView) findViewById(R.id.listTag);
        arrayTag = new ArrayList<>();

        adapter = new TagAdapter(this, R.layout.dong_tag, arrayTag);
        listTag.setAdapter(adapter);

        noteDatabase = new Database(this, "database.sqlite", null, 1);

        noteDatabase.QueryData("CREATE TABLE IF NOT EXISTS Tag(Id INTEGER PRIMARY KEY AUTOINCREMENT, tag VARCHAR(250))");
        GetDataTag();

    }

    private void GetDataTag() {
        Cursor dataGhiChu = noteDatabase.GetData("SELECT * FROM Tag");
        arrayTag.clear();
        while (dataGhiChu.moveToNext()) {
            String name = dataGhiChu.getString(1);
            int id = dataGhiChu.getInt(0);
            arrayTag.add(new Tag(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.itemAddTag) {
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_note);

        final EditText edtGC = dialog.findViewById(R.id.edittextThemGC);
        TextView textViewTag = dialog.findViewById(R.id.textThemGC);
        Button btnThemNote = dialog.findViewById(R.id.buttonThemNote);
        Button btnHuyNote = dialog.findViewById(R.id.buttonHuyNote);
        textViewTag.setText("Thêm Tag");
        edtGC.setHint("Nhập tag");

        btnThemNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNote = edtGC.getText().toString();
                if (tenNote.equals("")) {
                    Toast.makeText(TagActivity.this, "Vui lòng nhập tên ghi chú", Toast.LENGTH_SHORT).show();
                } else {
                    noteDatabase.QueryData("INSERT INTO Tag VALUES(null,'"+ tenNote +"')");
                    Toast.makeText(TagActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataTag();
                }

            }
        });

        btnHuyNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void DialogSuaCongViec(final String name, final int id) {
        Intent i = new Intent(TagActivity.this, AddElementTagActivity.class);;
        i.putExtra("title", name);
        startActivity(i);
    }

    @Override
    public void DialogXoaCV(final String tencv, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa ghi chú "+ tencv +" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteDatabase.QueryData("DELETE FROM Tag WHERE Id = '"+ id +"'");
                Toast.makeText(TagActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                Cursor dataCongViec = noteDatabase.GetData("Select * from CongViec1");
                while (dataCongViec.moveToNext()) {
                    int id1 = dataCongViec.getInt(0);
                    String tag = dataCongViec.getString(4);
                    String newtag = tag.replace("/"+tencv,"");
                    noteDatabase.QueryData("UPDATE CongViec1 SET Tag = '"+ newtag + "' WHERE Id = '" + id1 + "'");
                }
                Cursor dataViecLam = noteDatabase.GetData("Select * from ViecLam1");
                while (dataViecLam.moveToNext()) {
                    int id1 = dataViecLam.getInt(0);
                    String tag = dataViecLam.getString(2);
                    String newtag = tag.replace("/"+tencv,"");
                    noteDatabase.QueryData("UPDATE ViecLam1 SET Tag = '"+ newtag + "' WHERE Id = '" + id1 + "'");
                }
                Cursor dataGhiChu = noteDatabase.GetData("Select * from GhiChu2");
                while (dataGhiChu.moveToNext()) {
                    int id1 = dataGhiChu.getInt(0);
                    String tag = dataGhiChu.getString(4);
                    String newtag = tag.replace("/"+tencv,"");
                    noteDatabase.QueryData("UPDATE GhiChu2 SET Tag = '"+ newtag + "' WHERE Id = '" + id1 + "'");
                }
                GetDataTag();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialogXoa.show();
    }
    public void chonTag(int id, String name){
        Intent i = new Intent(TagActivity.this, ShowTagActivity.class);
        i.putExtra("title", name);
        startActivity(i);
    }
}