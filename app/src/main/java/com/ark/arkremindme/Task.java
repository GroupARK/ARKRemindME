package com.ark.arkremindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

<<<<<<< HEAD
import android.annotation.SuppressLint;
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
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
import java.util.zip.DeflaterInputStream;

public class Task extends AppCompatActivity implements AppManager {

    Database taskDatabase;

    ListView lvViecLam;
    ArrayList<ViecLam> arrayViecLam;
    ViecLamAdapter adapter;

<<<<<<< HEAD
    @SuppressLint("RestrictedApi")
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
<<<<<<< HEAD
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
=======
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        lvViecLam = (ListView) findViewById(R.id.listviewTask);
        arrayViecLam = new ArrayList<>();

        adapter = new ViecLamAdapter(this, R.layout.dong_viec_lam, arrayViecLam);
        lvViecLam.setAdapter(adapter);

<<<<<<< HEAD
        taskDatabase = new Database(this, "database.sqlite", null, 1);

        taskDatabase.QueryData("CREATE TABLE IF NOT EXISTS ViecLam1(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenVL VARCHAR(250), Tag VARCHAR(250))");
=======
        taskDatabase = new Database(this, "task.sqlite", null, 1);

        taskDatabase.QueryData("CREATE TABLE IF NOT EXISTS ViecLam(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenVL VARCHAR(250))");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        GetDataViecLam();
    }

    private void GetDataViecLam() {
<<<<<<< HEAD
        Cursor dataViecLam = taskDatabase.GetData("SELECT * FROM ViecLam1");
=======
        Cursor dataViecLam = taskDatabase.GetData("SELECT * FROM ViecLam");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
        arrayViecLam.clear();
        while (dataViecLam.moveToNext()) {
            String name = dataViecLam.getString(1);
            int id = dataViecLam.getInt(0);
<<<<<<< HEAD
            arrayViecLam.add(new ViecLam(Task.this, name, id, false,""));
=======
            arrayViecLam.add(new ViecLam(Task.this, name, id, false));
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.menuSearchTask);
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

        if (item.getItemId() == R.id.menuAddTask) {
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dong_them_task);

        final EditText edtVL = (EditText) dialog.findViewById(R.id.edittextThemVL);
        Button btnThemVL = (Button) dialog.findViewById(R.id.buttonThemVL);
        Button btnHuyVL = (Button) dialog.findViewById(R.id.buttonHuyVL);

        btnThemVL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTask = edtVL.getText().toString();
                if (tenTask.equals("")) {
                    Toast.makeText(Task.this, "Vui lòng nhập tên việc làm", Toast.LENGTH_SHORT).show();
                } else {
<<<<<<< HEAD
                    taskDatabase.QueryData("INSERT INTO ViecLam1 VALUES(null,'"+ tenTask +"', 'all')");
=======
                    taskDatabase.QueryData("INSERT INTO ViecLam VALUES(null,'"+ tenTask +"')");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
                    Toast.makeText(Task.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataViecLam();
                }
            }
        });

        btnHuyVL.setOnClickListener(new View.OnClickListener() {
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
        dialog.setContentView(R.layout.dialog_suatask);
        dialog.show();

        final EditText edtViecLam = (EditText) dialog.findViewById(R.id.edittextXNVL);
        Button btnViecLam = (Button) dialog.findViewById(R.id.buttonXNVL);

        edtViecLam.setText(name);
        btnViecLam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtViecLam.getText().toString().trim();
<<<<<<< HEAD
                taskDatabase.QueryData("UPDATE ViecLam1 SET TenVL = '"+ tenMoi +"' WHERE Id = '"+ id +"'");
=======
                taskDatabase.QueryData("UPDATE ViecLam SET TenVL = '"+ tenMoi +"' WHERE Id = '"+ id +"'");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
                Toast.makeText(Task.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataViecLam();
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
<<<<<<< HEAD
                taskDatabase.QueryData("DELETE FROM ViecLam1 WHERE Id = '"+ id +"'");
=======
                taskDatabase.QueryData("DELETE FROM ViecLam WHERE Id = '"+ id +"'");
>>>>>>> 98e64522bdedbaae23006a45d29e5f259c08efed
                Toast.makeText(Task.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataViecLam();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogXoa.show();
    }

    public void editContentVL(int id){
        Intent i = new Intent(Task.this, Noidungvieclam.class);
        String title = arrayViecLam.get(id).getTenVL();
        i.putExtra("title",title);
        startActivity(i);
    }

}
