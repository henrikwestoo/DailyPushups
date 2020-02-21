package com.example.dailypushups;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(version = 2, entities = {Entry.class})
public abstract class EntryDatabase extends RoomDatabase {

    private static final String DbName = "entry_db";

    public static EntryDatabase getDbInstance(Context context){

        EntryDatabase db = Room.databaseBuilder(context, EntryDatabase.class, DbName).addMigrations().addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();
        return db;

    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ENTRY "
                    + " ADD COLUMN Date VARCHAR(10)");
        }
    };

    public abstract EntryDao entryDao();


}
