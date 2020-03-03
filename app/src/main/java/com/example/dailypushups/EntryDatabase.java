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

    //används för att instansiera databasen
    public static EntryDatabase getDbInstance(Context context){

        //Jag tillåter att databasoperationer sker i applikationens maintråd för enkelhetens skull
        //hade jag använt större datamängder kunde detta resulterat i att GUIn hänger upp sig
        EntryDatabase db = Room.databaseBuilder(context, EntryDatabase.class, DbName).addMigrations().addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();
        return db;

    }

    //jag la till en kolumn efter att gjorde färdigt databasen
    //därför krävdes en migration
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE ENTRY "
                    + " ADD COLUMN Date VARCHAR(10)");
        }
    };

    //används för att komma åt EntryDaon, som vi använder för att kommunicera med databasen
    public abstract EntryDao entryDao();

}
