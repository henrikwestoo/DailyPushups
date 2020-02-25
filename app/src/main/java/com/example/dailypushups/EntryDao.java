package com.example.dailypushups;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    //hämtar alla entries
    @Query("SELECT * FROM ENTRY")
    List<Entry> getAll();

    //hämtar den senaste entryn
    @Query("SELECT * FROM ENTRY LIMIT 1")
    Entry getLatest();

    //hämtar den näst-senaste databasraden, dvs gårdagens antal armhävningar
    @Query("SELECT * FROM (SELECT * FROM ENTRY ORDER BY ID DESC LIMIT 2) ORDER BY ID ASC LIMIT 1")
    Entry getNextToLatest();

    //hämtar en specifik entry, används för att kontrollera om användaren
    //redan angett antalet armhävningar för en dag
    @Query("SELECT * FROM ENTRY WHERE DATE = :date LIMIT 1" )
    Entry getSpecificEntry(String date);

    //används för att uppdatera en befintlig rad
    @Query("UPDATE ENTRY SET Pushups = :pushups WHERE DATE = :date" )
    void updateSpecificEntry(String date, int pushups);

    //skapar en ny rad
    @Insert
    void insertEntry(Entry entry);

    //tar bort alla rader
    @Query("DELETE FROM ENTRY")
    void deleteAllEntries();


}
