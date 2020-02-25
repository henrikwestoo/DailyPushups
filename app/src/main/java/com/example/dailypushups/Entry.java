package com.example.dailypushups;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ENTRY")
public class Entry {

    public Entry(int pushups, String date){
        this.pushups = pushups;
        this.date = date;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Pushups")
    public int pushups;

    @ColumnInfo(name = "Date")
    public String date;
}
