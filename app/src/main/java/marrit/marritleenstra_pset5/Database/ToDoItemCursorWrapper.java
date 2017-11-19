package marrit.marritleenstra_pset5.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import marrit.marritleenstra_pset5.ToDoItem;



/**
 * Created by Marrit on 18-10-2017.
 * A CursorWrapper to pull out data of the database.
 * Based on: Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch guide.
 * 3th edition. Chapter 14.
 */

public class ToDoItemCursorWrapper extends CursorWrapper {
    public ToDoItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDoItem getToDoItem() {
        String idString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_todos._id));
        String title = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_todos.TITLE));
        int isCompleted = getInt(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_todos.COMPLETED));
        String idListString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_todos.id_list));

        ToDoItem toDoItem = new ToDoItem(Integer.valueOf(idString));
        toDoItem.setTitle(title);
        toDoItem.setCompleted(isCompleted != 0);
        toDoItem.setIdList(Integer.valueOf(idListString));
        System.out.println("make new todo item, listnumber = " + toDoItem.getIdList());

        return toDoItem;
    }
}

