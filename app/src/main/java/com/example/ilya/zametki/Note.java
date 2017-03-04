package com.example.ilya.zametki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Note extends AppCompatActivity {
    private EditText nodeName;
    private EditText nodeText;
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        nodeName = (EditText) findViewById(R.id.etName);
        nodeText = (EditText) findViewById(R.id.etText);
        nodeName.setFocusableInTouchMode(true);
        nodeName.setFocusable(true);
        nodeName.setCursorVisible(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save: {
                if (nodeName.getText().toString()!="") {
                    mIntent = new Intent();
                    mIntent.putExtra("NoteNameAdd", nodeName.getText().toString());
                    mIntent.putExtra("NoteTextAdd", nodeText.getText().toString());
                    setResult(RESULT_OK, mIntent);
                } else {
                   setResult(RESULT_CANCELED, mIntent);
                }
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
