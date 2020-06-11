package com.ark.arkremindme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button noteButton;
    Button todoListButton;
    Button taskButton;
    Button tagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteButton = (Button) findViewById(R.id.noteClick);
        todoListButton = (Button) findViewById(R.id.todoClick);
        taskButton = (Button) findViewById(R.id.taskClick);
        tagButton = findViewById(R.id.tagClick);

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Note.class));
            }
        });

        todoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TodoList.class));
            }
        });

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Task.class));
            }
        });

        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TagActivity.class));
            }
        });
    }
}
