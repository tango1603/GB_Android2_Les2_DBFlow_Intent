package com.example.ilya.zametki;

import java.util.HashMap;


/**
 * Created by Ilya on 01.03.2017.
 */

public class Data {

    private HashMap<String, String> note_map = new HashMap<>();

    public Data(HashMap input_map) {
        note_map = input_map;
    }

    public void AddNote(String note_name, String note_text)
    {
        note_map.put(note_name, note_text);
    }

    public void DeleteNote(String note_name) {
        note_map.remove(note_name);
    }

}
