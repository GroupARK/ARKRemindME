package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Note extends AppCompatActivity implements AppManager {

    Database noteDatabase;

    ListView lvGhiChu;
    ArrayList<GhiChu> arrayGhiChu;
    GhiChuAdapter adapter;
    Calendar c;
    @SuppressLint("RestrictedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportActionBar().setTitle("List Note");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        lvGhiChu = (ListView) findViewById(R.id.listviewGhiChu);
        arrayGhiChu = new ArrayList<>();
        c = Calendar.getInstance();

        adapter = new GhiChuAdapter(this, R.layout.dong_ghi_chu, arrayGhiChu);
        lvGhiChu.setAdapter(adapter);

        noteDatabase = new Database(this, "database.sqlite", null, 1);

        noteDatabase.QueryData("CREATE TABLE IF NOT EXISTS GhiChu2(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenGC VARCHAR(250), Ngay DATE, ThoiGian TIME, Tag VACHAR(250))");

        GetDataGhiChu();

    }

    private void GetDataGhiChu() {
        Cursor dataGhiChu = noteDatabase.GetData("SELECT * FROM GhiChu2");
        arrayGhiChu.clear();
        while (dataGhiChu.moveToNext()) {
            String name = dataGhiChu.getString(1);
            int id = dataGhiChu.getInt(0);
            String todayDate = dataGhiChu.getString(2);
            String currentTime = dataGhiChu.getString(3);
            arrayGhiChu.add(new GhiChu(Note.this, id, name, todayDate, currentTime, false, ""));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.menuSearchNote);

        SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuAddNote) {
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_note);

        final EditText edtGC = dialog.findViewById(R.id.edittextThemGC);
        Button btnThemNote = dialog.findViewById(R.id.buttonThemNote);
        Button btnHuyNote = dialog.findViewById(R.id.buttonHuyNote);

        btnThemNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNote = edtGC.getText().toString();
                String ngayNote = chuan(c.get(Calendar.DAY_OF_MONTH))+"/"+chuan(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
                String thoigianNote = chuan(c.get(Calendar.HOUR))+":"+chuan(c.get(Calendar.MINUTE));
                if (tenNote.equals("") && ngayNote.equals("") && thoigianNote.equals("")) {
                    Toast.makeText(Note.this, "Vui lòng nhập tên ghi chú", Toast.LENGTH_SHORT).show();
                } else {
                    noteDatabase.QueryData("INSERT INTO GhiChu2 VALUES(null,'"+ tenNote +"','"+ ngayNote +"','"+ thoigianNote +"', 'all')");
                    Toast.makeText(Note.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataGhiChu();
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

    private String chuan(int i) {
        if(i < 10)
            return "0"+i;
        return String.valueOf(i);
    }

    @Override
    public void DialogSuaCongViec(final String name, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_suanote);
        dialog.show();

        final EditText edtGhiChu = (EditText) dialog.findViewById(R.id.edittextXNGC);
        Button btnXN = (Button) dialog.findViewById(R.id.buttonXNGC);

        edtGhiChu.setText(name);

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtGhiChu.getText().toString().trim();
                String ngayMoi = chuan(c.get(Calendar.DAY_OF_MONTH))+"/"+chuan(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
                String thoigianMoi = chuan(c.get(Calendar.HOUR))+":"+chuan(c.get(Calendar.MINUTE));
                noteDatabase.QueryData("UPDATE GhiChu2 SET TenGC = '"+ tenMoi +"', Ngay = '"+ ngayMoi +"', ThoiGian = '"+ thoigianMoi +"' WHERE Id = '"+ id +"'");
                Toast.makeText(Note.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataGhiChu();

            }
        });

    }

    @Override
    public void DialogXoaCV(final String tencv, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa ghi chú "+ tencv +" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteDatabase.QueryData("DELETE FROM GhiChu2 WHERE Id = '"+ id +"'");
                Toast.makeText(Note.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataGhiChu();
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
