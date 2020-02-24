package com.example.dailypushups;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM ENTRY")
    List<Entry> getAll();

    @Query("SELECT * FROM ENTRY LIMIT 1")
    Entry getLatest();

    //hämtar den näst-senaste databasraden, dvs gårdagens antal armhävningar
    @Query("SELECT * FROM (SELECT * FROM ENTRY ORDER BY ID DESC LIMIT 2) ORDER BY ID ASC LIMIT 1")
    Entry getNextToLatest();

    @Query("SELECT * FROM ENTRY WHERE DATE = :name")
    Entry getYesterdays(String name);

    @Insert
    void insertEntry(Entry entry);

    @Query("DELETE FROM ENTRY")
    void deleteAllEntires();


}
