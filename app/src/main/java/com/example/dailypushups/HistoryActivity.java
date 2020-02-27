package com.example.dailypushups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Log.d("tag", "history opened");


        ArrayList<String> data = new ArrayList<>();

        EntryDatabase db = EntryDatabase.getDbInstance(this);
        List<Entry> entries =  db.entryDao().getAll();

        for (Entry entry : entries)

        {

            data.add(entry.date + " " + entry.pushups);

        }

        ListView listView = (ListView) findViewById(R.id.historyListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);

        listView.setAdapter(adapter);


    }
}
