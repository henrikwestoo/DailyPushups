package com.example.dailypushups;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//entitetsklass för databasen

@Entity(tableName = "ENTRY")
public class Entry {

    public Entry(int pushups, String date){
        this.pushups = pushups;
        this.date = date;
    }

    //ett id som primärnyckel enligt god databassed
    @PrimaryKey(autoGenerate = true)
    public int id;

    //vi lagrar antal armhävningar
    @ColumnInfo(name = "Pushups")
    public int pushups;

    //vi lagrar datumet som armhävningarna gjordes på
    @ColumnInfo(name = "Date")
    public String date;
}
