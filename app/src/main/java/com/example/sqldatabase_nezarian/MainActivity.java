package com.example.sqldatabase_nezarian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DatabaseHelper dbHelper;


    final String[] from = new String[] {dbHelper._ID, dbHelper.TITLE, dbHelper.DESC};
    final int[] to = new int[]{R.id.id, R.id.listTitle, R.id.listDesc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.myListView);

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to, 0);
        listView.setAdapter(adapter);

        try {
            Intent intent = getIntent();
            Boolean ItemDeleted = intent.getExtras().getBoolean("Item Deleted");
            ModifyActivity modifyActivity = new ModifyActivity();
        } catch (Exception e) {
            if (adapter.isEmpty()) {
                Snackbar.make(listView, "Klik tombol tambah untuk menambahkan data", 2000).show();
            } else {
                Snackbar.make(listView, "Tahan data untuk memodifikasi data", 2000).show();
            }
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOption(view);
                return false;
            }
        });

    }



    public void showOption(View view) {
        String[] option = {"Edit", "Delete"};
        TextView itemID = (TextView) findViewById(R.id.id);
        TextView itemTitle = (TextView) findViewById(R.id.listTitle);
        TextView itemDesc = (TextView) findViewById(R.id.listDesc);

        final String myId = itemID.getText().toString();
        final String myTitle = itemTitle.getText().toString();
        final String myDesc = itemDesc.getText().toString();

        AlertDialog.Builder opsi = new AlertDialog.Builder(MainActivity.this);
        opsi.setTitle("Choose Option");
        opsi.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);

                    intent.putExtra("Id", myId);
                    intent.putExtra("Title", myTitle);
                    intent.putExtra("Desc", myDesc);
                    startActivity(intent);
                }
                else {
                    deleteData();
                }
            }
        });
        AlertDialog dialog = opsi.create();
        dialog.show();
    }

    public void onClickAddItem(View view) {
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        startActivity(intent);
    }

    public void deleteData() {
        TextView itemID = (TextView) findViewById(R.id.id);
        final String myId = itemID.getText().toString();

        dbManager.delete(Integer.parseInt(myId));
        Intent intent = getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}