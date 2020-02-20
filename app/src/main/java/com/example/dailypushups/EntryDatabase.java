package com.example.dailypushups;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Entry.class})
public abstract class EntryDatabase extends RoomDatabase {

    private static final String DbName = "entry_db";

    public static EntryDatabase getDbInstance(Context context){

        EntryDatabase db = Room.databaseBuilder(context, EntryDatabase.class, DbName).build();
        return db;

    }

    public abstract EntryDao entryDao();


}
