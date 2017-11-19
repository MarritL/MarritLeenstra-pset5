package marrit.marritleenstra_pset5.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static marrit.marritleenstra_pset5.Database.ToDoDBSchema.ToDoTable;
import static marrit.marritleenstra_pset5.Database.ToDoDBSchema.ToDoTable.TABLE_LISTS;
import static marrit.marritleenstra_pset5.Database.ToDoDBSchema.ToDoTable.TABLE_TODOS;

/**
 * Created by Marrit on 5-11-2017.
 * Singleton class to create a database.
 * Partly based on: Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch
 * guide. 3th edition. Chapter 14.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ManyLists.db";

    // Method to retrieve the current instance of the singleton
    // if it does'n ot exist yet, then it will create one with the application context
    public static synchronized  DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    // create database the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table ToDos
        db.execSQL("create table " + TABLE_TODOS + "(" +
                ToDoDBSchema.ToDoTable.Cols_todos._id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoTable.Cols_todos.TITLE + ", " +
                ToDoTable.Cols_todos.COMPLETED + ", " +
                ToDoTable.Cols_todos.id_list + " INTEGER" + ")"
        );

        // create table lists
        db.execSQL("create table " + TABLE_LISTS + "(" +
                ToDoDBSchema.ToDoTable.Cols_lists._id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoTable.Cols_lists.TITLE + ")"
        );
    }

        // when new version of database is needed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

}
