package com.example.ilya.zametki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoteList extends AppCompatActivity {

    private ListView lv_notes;
    private ArrayAdapter<String> mStringArrayAdapter;
    private List<String> elements = new ArrayList<>();
    private Intent mIntent;
    final private int REQUEST_CODE_NOTE = 1;
    private String noteName;
    private SharedPreferences mSPrefs;


    /**
     * Сохраняем список
     */
    private void saveActState() {
        SharedPreferences.Editor editor = mSPrefs.edit();
        editor.clear();
        for (int i = 0; i < mStringArrayAdapter.getCount(); ++i) {
            editor.putString(String.valueOf(i), mStringArrayAdapter.getItem(i));
        }
        editor.commit();
    }

    /**
     * Загружаем список
     */
    private void loadActState() {
        for (int i = 0; ; ++i) {
            final String str = mSPrefs.getString(String.valueOf(i), "");
            if (!str.equals("")) {
                mStringArrayAdapter.add(str);
            } else {
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveActState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv_notes = (ListView) findViewById(R.id.list_notes);
        mStringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elements);
        lv_notes.setAdapter(mStringArrayAdapter);
        lv_notes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_notes.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                //обработчик выделения пунктов списка ActionMode
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // обработка нажатия на пункт ActionMode
                // в данном случае просто закрываем меню
                mode.finish();
                return false;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Устанавливаем для ActionMode меню
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // вызывается при закрытии ActionMode
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // вызывается при обновлении ActionMode
                // true если меню или ActionMode обновлено иначе false
                return false;
            }
        });

       // registerForContextMenu(lv_notes);
        loadActState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add: {
                mIntent = new Intent(this, Note.class);
                startActivityForResult(mIntent, REQUEST_CODE_NOTE);
                return true;
            }
            case R.id.menu_clear: {
                clearList();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NOTE: {
                    noteName=data.getStringExtra("NoteNameAdd");
                    if (!noteName.isEmpty()) {
                        addElement(noteName);
                        mStringArrayAdapter.notifyDataSetChanged();
                    }
                        break;
                }
            }
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editElement(info.position);
                return true;
            case R.id.menu_Delete:
                deleteElement(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void clearList() {
        elements.clear();
        mStringArrayAdapter.notifyDataSetChanged();
    }

    private void addElement(String noteName) {
        elements.add(noteName);
        mStringArrayAdapter.notifyDataSetChanged();
    }

    private void editElement(int id) {
        elements.set(id, "Edited");
        mStringArrayAdapter.notifyDataSetChanged();
    }

    private void deleteElement(int id) {
        elements.remove(id);
        mStringArrayAdapter.notifyDataSetChanged();
    }
}
