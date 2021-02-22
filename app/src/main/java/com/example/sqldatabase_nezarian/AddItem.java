package com.example.sqldatabase_nezarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.snackbar.Snackbar;

public class AddItem extends AppCompatActivity {
    private EditText inputTitle;
    private EditText inputDesc;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputTitle = (EditText) findViewById(R.id.inputItem);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

        dbManager = new DatabaseManager(this);
        dbManager.open();
    }

    public void onClickDone(View view) {
        String myInputTitle = inputTitle.getText().toString();
        String myInputDesc = inputDesc.getText().toString();

        if (myInputTitle.isEmpty() || myInputDesc.isEmpty()) {
            Snackbar.make(view, "Please fill in both form!", Snackbar.LENGTH_SHORT).show();
        } else {
            dbManager.insert(myInputTitle, myInputDesc);
            Intent intent = new Intent(AddItem.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
}