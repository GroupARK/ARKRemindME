package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
=======
import android.app.AlertDialog;
import android.app.Dialog;
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
=======
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

public class TodoList extends AppCompatActivity implements AppManager {

    Database database;

    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
<<<<<<< HEAD
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;


    @SuppressLint("RestrictedApi")
=======

>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
<<<<<<< HEAD
        getSupportActionBar().setTitle("Todo List");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        lvCongViec = (ListView) findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

<<<<<<< HEAD
        database = new Database(this, "database.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec1(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(250), Ngay DATE, ThoiGian TIME, tag VARCHAR(250))");
=======
        database = new Database(this, "TodoList.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(250), Ngay DATE, ThoiGian TIME)");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        GetDataCongViec();
    }

    private void GetDataCongViec() {
<<<<<<< HEAD
        Cursor dataCongViec = database.GetData(("SELECT * FROM CongViec1"));
=======
        Cursor dataCongViec = database.GetData(("SELECT * FROM CongViec"));
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()) {
            String name = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            String date = dataCongViec.getString(2);
            String time = dataCongViec.getString(3);
<<<<<<< HEAD
            arrayCongViec.add(new CongViec(TodoList.this, id, name, date, time, false, ""));
=======
            arrayCongViec.add(new CongViec(TodoList.this, id, name, date, time, false));
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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
<<<<<<< HEAD
        final Calendar c = Calendar.getInstance();
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        final EditText edtTen = (EditText) dialog.findViewById(R.id.edittextThemCV);
        final EditText edtNgay = dialog.findViewById(R.id.textNgayCV);
        final EditText edtThoiGian = dialog.findViewById(R.id.textThoiGianCV);

        Button btnThem = dialog.findViewById(R.id.buttonThem);
        Button btnHuy = dialog.findViewById(R.id.buttonHuy);

<<<<<<< HEAD
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog1 = new DatePickerDialog(TodoList.this, onDateSetListener, year, month, day);
                dialog1.getWindow();
                dialog1.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                edtNgay.setText( chuan(dayOfMonth) + "/" + chuan(month)+"/"+year);
            }
        };
        edtThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog dialog2 = new TimePickerDialog(TodoList.this, onTimeSetListener, hour, minute,true);
                dialog2.getWindow();
                dialog2.show();
            }
        });
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edtThoiGian.setText(chuan(hourOfDay)+":"+chuan(minute));
            }
        };
=======
        edtNgay.setText("DD/MM/YYYY");
        edtThoiGian.setText("HH:MM");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                String ngayCV = edtNgay.getText().toString();
                String thoigianCV = edtThoiGian.getText().toString();
                if (tencv.equals("") && ngayCV.equals("") && thoigianCV.equals("")) {
                    Toast.makeText(TodoList.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                } else {
<<<<<<< HEAD
                    database.QueryData("INSERT INTO CongViec1 VALUES(null, '" + tencv + "', '"+ ngayCV +"', '"+ thoigianCV +"', 'all')");
=======
                    database.QueryData("INSERT INTO CongViec VALUES(null, '" + tencv + "', '"+ ngayCV +"', '"+ thoigianCV +"')");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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

<<<<<<< HEAD
    private String chuan(int i) {
        if(i < 10)
            return "0"+i;
        else
            return String.valueOf(i);
    }

=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
    @Override
    public void DialogSuaCongViec(final String name, final int id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);
<<<<<<< HEAD
        final Calendar c = Calendar.getInstance();
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
        dialog.show();

        final EditText edtTenCV = dialog.findViewById(R.id.edittextXNCV);
        final EditText edtNgayCV = dialog.findViewById(R.id.edittextNgayCV);
        final EditText edtThoiGianCV = dialog.findViewById(R.id.edittextThoiGianCV);
<<<<<<< HEAD
        edtNgayCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog1 = new DatePickerDialog(TodoList.this, onDateSetListener, year, month, day);
                dialog1.getWindow();
                dialog1.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                edtNgayCV.setText( chuan(dayOfMonth) + "/" + chuan(month)+"/"+year);
            }
        };
        edtThoiGianCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog dialog2 = new TimePickerDialog(TodoList.this, onTimeSetListener, hour, minute,true);
                dialog2.getWindow();
                dialog2.show();
            }
        });
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edtThoiGianCV.setText(chuan(hourOfDay)+":"+chuan(minute));
            }
        };
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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
<<<<<<< HEAD
                database.QueryData("UPDATE CongViec1 SET TenCV  = '"+ tenMoi +"', Ngay = '"+ ngayMoi +"', ThoiGian = '"+ thoigianMoi +"' WHERE Id = '"+ id +"'");
=======
                database.QueryData("UPDATE CongViec SET TenCV  = '"+ tenMoi +"', Ngay = '"+ ngayMoi +"', ThoiGian = '"+ thoigianMoi +"' WHERE Id = '"+ id +"'");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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
<<<<<<< HEAD
                database.QueryData("DELETE FROM CongViec1 WHERE Id = '" + id + "'");
=======
                database.QueryData("DELETE FROM CongViec WHERE Id = '" + id + "'");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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
