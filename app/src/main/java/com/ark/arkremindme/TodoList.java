package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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

public class TodoList extends AppCompatActivity implements AppManager {

    Database database;

    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        lvCongViec = (ListView) findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        database = new Database(this, "TodoList.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(250), Ngay DATE, ThoiGian TIME)");

        GetDataCongViec();
    }

    private void GetDataCongViec() {
        Cursor dataCongViec = database.GetData(("SELECT * FROM CongViec"));
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            String name = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            String date = dataCongViec.getString(2);
            String time = dataCongViec.getString(3);
            arrayCongViec.add(new CongViec(TodoList.this, id, name, date, time, false));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_todo, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.menuSearch);

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
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        final EditText edtTen = (EditText) dialog.findViewById(R.id.edittextThemCV);
        final EditText edtNgay = dialog.findViewById(R.id.textNgayCV);
        final EditText edtThoiGian = dialog.findViewById(R.id.textThoiGianCV);

        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

        edtNgay.setText("DD/MM/YYYY");
        edtThoiGian.setText("HH:MM");

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                String ngayCV = edtNgay.getText().toString();
                String thoigianCV = edtThoiGian.getText().toString();
                if (tencv.equals("") && ngayCV.equals("") && thoigianCV.equals("")) {
                    Toast.makeText(TodoList.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '" + tencv + "', '"+ ngayCV +"', '"+ thoigianCV +"')");
                    Toast.makeText(TodoList.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void DialogSuaCongViec(final String name, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);
        dialog.show();

        final EditText edtTenCV = dialog.findViewById(R.id.edittextXNCV);
        final EditText edtNgayCV = dialog.findViewById(R.id.edittextNgayCV);
        final EditText edtThoiGianCV = dialog.findViewById(R.id.edittextThoiGianCV);
        Button btnXacNhan = dialog.findViewById(R.id.buttonXNCV);

        edtTenCV.setText(name);
        edtNgayCV.setText("NN/TT/NNNN");
        edtThoiGianCV.setText("GG:PP");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenCV.getText().toString();
                String ngayMoi = edtNgayCV.getText().toString();
                String thoigianMoi = edtThoiGianCV.getText().toString();
                database.QueryData("UPDATE CongViec SET TenCV  = '"+ tenMoi +"', Ngay = '"+ ngayMoi +"', ThoiGian = '"+ thoigianMoi +"' WHERE Id = '"+ id +"'");
                Toast.makeText(TodoList.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCongViec();
            }
        });


    }

    @Override
    public void DialogXoaCV(final String tencv, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc" + tencv +" này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
                Toast.makeText(TodoList.this, "Đã xóa " + tencv, Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }

    public void editContentCV(int id){
        Intent i = new Intent(this, TodoListShow.class);
        String title = arrayCongViec.get(id).getTenCV();
        i.putExtra("title",title);
        startActivity(i);
    }

}
