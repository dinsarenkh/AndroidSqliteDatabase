package com.dinsaren.androidsqlitedatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dinsaren.androidsqlitedatabase.models.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TITLE = "TITLE";
    public static final String ID = "ID";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String NOTE_DB = "note_db";
    public static final String NOTE_TABLE = "NOTE_TABLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, NOTE_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement = "CREATE TABLE " + NOTE_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + DESCRIPTION + " TEXT)";
        sqLiteDatabase.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + NOTE_TABLE);
    }

    public boolean create(Note person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, person.getTitle());
        contentValues.put(DESCRIPTION, person.getDescription());
        return db.insert(NOTE_TABLE, null, contentValues) != -1;
    }

    public boolean update(Note person) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, person.getTitle());
        contentValues.put(DESCRIPTION, person.getDescription());
        long result = db.update(NOTE_TABLE, contentValues, ID + " = " + person.getId(), null);
        return result != -1;
    }

    public List<Note> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> personList = new ArrayList<>();
        String sql = "SELECT * FROM " + NOTE_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                personList.add(new Note(id, title, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return personList;
    }

    public Note getById(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Note noted = new Note();
        String sql = "SELECT * FROM " + NOTE_TABLE + " WHERE ID='" + id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int noteId = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                noted = new Note(noteId, title, description);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return noted;
    }


}
