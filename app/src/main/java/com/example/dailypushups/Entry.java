package com.example.dailypushups;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ENTRY")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Pushups")
    public int pushups;
}
