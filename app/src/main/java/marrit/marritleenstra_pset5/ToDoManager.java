package marrit.marritleenstra_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import marrit.marritleenstra_pset5.Database.ToDoDBSchema.ToDoTable;
import marrit.marritleenstra_pset5.Database.ToDoItemCursorWrapper;
import marrit.marritleenstra_pset5.Database.DatabaseHelper;
import marrit.marritleenstra_pset5.Database.ToDoListCursorWrapper;

/**
 * Created by Marrit on 12-10-2017.
 * The To-Do lab contains all the methods to manipulate the data stash for ToDoItem objects.
 */

class ToDoManager {

    // declare variables
    private static ToDoManager sManagerInstance;
    private static SQLiteDatabase mDatabase;

    // getInstance the ToDoManager object (only one possible)
    static ToDoManager getInstance(Context context) {
        if (sManagerInstance == null){
            sManagerInstance = new ToDoManager(context);
        }
        return sManagerInstance;
    }

    // open (and if needed create) a database
    private ToDoManager(Context context) {
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        mDatabase = helper.getWritableDatabase();
    }

    // add a row to the ToDoTable
    void addToDo(ToDoItem toDo) {
        ContentValues values = getContentValues(toDo);

        mDatabase.insert(ToDoTable.TABLE_TODOS, null, values);
        System.out.println("values = " + values);
    }

    // add a row to the ListsTable
    void addToDoList(ToDoList toDoList) {
        ContentValues values = getListContentValues(toDoList);

        mDatabase.insert(ToDoTable.TABLE_LISTS, null, values);
    }

    // delete a row from the ToDoTable
    void deleteToDo(ToDoItem toDo) {
        String idString = String.valueOf(toDo.getId());

        mDatabase.delete(ToDoTable.TABLE_TODOS, ToDoTable.Cols_todos._id + " = ?",
                new String[] { idString });



    }

    // delete a row from the Lists table
    void deleteToDoList(ToDoList toDoList) {
        String idString = String.valueOf(toDoList.getID());

        mDatabase.delete(ToDoTable.TABLE_LISTS, ToDoTable.Cols_lists._id + " = ?",
                new String[] { idString });

        System.out.println("in delete list, idstring = " + idString);
    }

    // read the ToDoItems from the database and put in list
    List<ToDoItem> getToDoItems(Integer ListId) {
        //String mId = String.valueOf(ListId);
        List<ToDoItem> toDoItems = new ArrayList<>();

        String id = String.valueOf(ListId);
        String[] whereArgs = {id};

        ToDoItemCursorWrapper cursor = queryToDoItems(ToDoTable.Cols_todos.id_list + " = ?", whereArgs);


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                toDoItems.add(cursor.getToDoItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        System.out.println("length list= " + toDoItems.size());
        return toDoItems;
    }

    // read the ToDoLists from the database and put in a list
    List<ToDoList> getToDoListItems() {
        List<ToDoList> toDoLists = new ArrayList<>();

        ToDoListCursorWrapper cursor = queryToDoLists(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                toDoLists.add(cursor.getToDoListItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return toDoLists;
    }

    // getInstance one to do item
    public ToDoItem getToDoItem(int id) {

        ToDoItemCursorWrapper cursor = queryToDoItems(
                ToDoTable.Cols_todos._id + " = ?",
                new String[] {String.valueOf(id) }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getToDoItem();
        } finally {
            cursor.close();
        }
    }

    // update a row in the todos table
    void updateToDoItem(ToDoItem toDo) {
        String idString = String.valueOf(toDo.getId());
        ContentValues values = getContentValues(toDo);

        mDatabase.update(ToDoTable.TABLE_TODOS, values,
                ToDoTable.Cols_todos._id + " = ?",
                new String[] { idString });

    }

    // create a ContentValues for a to-do item
    private static ContentValues getContentValues(ToDoItem toDo) {
        ContentValues values = new ContentValues();
        values.put(ToDoTable.Cols_todos.TITLE, toDo.getTitle());
        values.put(ToDoTable.Cols_todos.id_list, toDo.getIdList());
        values.put(ToDoTable.Cols_todos.COMPLETED, toDo.getCompleted() ? 1: 0);

        return values;
    }

    // create a ContentValues for a to-do list item
    private static ContentValues getListContentValues(ToDoList toDoList) {
        ContentValues values = new ContentValues();
        values.put(ToDoTable.Cols_lists.TITLE, toDoList.getTitle());

        return values;
    }

    // query database todos table
    private static ToDoItemCursorWrapper queryToDoItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ToDoTable.TABLE_TODOS,
                null, // select all columns
                whereClause,
                whereArgs,
                null, // group by
                null, // having
                null // orderBy
        );
        System.out.println("queryToDoItems" + whereClause + whereArgs[0]);
        return new ToDoItemCursorWrapper(cursor);
    }

    // query database lists table
    private static ToDoListCursorWrapper queryToDoLists(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ToDoTable.TABLE_LISTS,
                null, // select all columns
                whereClause,
                whereArgs,
                null, // group by
                null, // having
                null // orderBy
        );
        return new ToDoListCursorWrapper(cursor);
    }
}
