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
import java.util.zip.DeflaterInputStream;

public class Task extends AppCompatActivity implements AppManager {

    Database taskDatabase;

    ListView lvViecLam;
    ArrayList<ViecLam> arrayViecLam;
    ViecLamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        lvViecLam = (ListView) findViewById(R.id.listviewTask);
        arrayViecLam = new ArrayList<>();

        adapter = new ViecLamAdapter(this, R.layout.dong_viec_lam, arrayViecLam);
        lvViecLam.setAdapter(adapter);

        taskDatabase = new Database(this, "task.sqlite", null, 1);

        taskDatabase.QueryData("CREATE TABLE IF NOT EXISTS ViecLam(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenVL VARCHAR(250))");

        GetDataViecLam();
    }

    private void GetDataViecLam() {
        Cursor dataViecLam = taskDatabase.GetData("SELECT * FROM ViecLam");
        arrayViecLam.clear();
        while (dataViecLam.moveToNext()) {
            String name = dataViecLam.getString(1);
            int id = dataViecLam.getInt(0);
            arrayViecLam.add(new ViecLam(Task.this, name, id, false));

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
                    taskDatabase.QueryData("INSERT INTO ViecLam VALUES(null,'"+ tenTask +"')");
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
                taskDatabase.QueryData("UPDATE ViecLam SET TenVL = '"+ tenMoi +"' WHERE Id = '"+ id +"'");
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
                taskDatabase.QueryData("DELETE FROM ViecLam WHERE Id = '"+ id +"'");
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
