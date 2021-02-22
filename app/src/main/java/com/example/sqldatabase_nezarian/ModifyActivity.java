
package com.example.sqldatabase_nezarian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


public class ModifyActivity extends AppCompatActivity {
    private EditText modTitle;
    private EditText modDesc;
    private long id;
    private boolean isItemDeleted = false;

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DatabaseManager(this);
        dbManager.open();

        modTitle = (EditText) findViewById(R.id.modTitle);
        modDesc = (EditText) findViewById(R.id.modDesc);

        Bundle intentData = getIntent().getExtras();

        final String myID = intentData.getString("Id");
        final String myTitle = intentData.getString("Title");
        final String myDesc = intentData.getString("Desc");

        modTitle.setText(myTitle);
        modDesc.setText(myDesc);
        id = Long.parseLong(myID);


        Button fabUpdate = (Button) findViewById(R.id.fabUpdate);

        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = modTitle.getText().toString();
                String newDesc = modDesc.getText().toString();
                dbManager.update(Integer.parseInt(myID), newTitle, newDesc);
                returnHome();
            }
        });
    }

    public void deleteData() {
        Bundle intentData = getIntent().getExtras();
        final String myID = intentData.getString("Id");
        id = Long.parseLong(myID);

        dbManager.delete(Integer.parseInt(myID));

    }

    public void returnHome() {
        Intent intent = new Intent (getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (isItemDeleted) {
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }

    public void setItemDeleted(boolean itemDeleted) {
        isItemDeleted = itemDeleted;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete){
            deleteData();
            setItemDeleted(true);
            returnHome();
        }
        return super.onOptionsItemSelected(item);

    }

}