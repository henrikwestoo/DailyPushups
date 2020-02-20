package com.example.dailypushups;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM ENTRY")
    List<Entry> getAll();

    @Query("SELECT * FROM ENTRY LIMIT 1")
    Entry getLatest();

    @Insert
    void insertEntry(Entry entry);

}
