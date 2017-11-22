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

    // constructor
    public ToDoItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDoItem getToDoItem() {
        // pull to-do out of database
        String idString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols._id));
        String title = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols.TITLE));
        int isCompleted = getInt(getColumnIndex(ToDoDBSchema.ToDoTable.Cols.COMPLETED));
        String idListString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols.id_list));

        // put the data in a ToDoItem
        ToDoItem toDoItem = new ToDoItem(Integer.valueOf(idString));
        toDoItem.setTitle(title);
        toDoItem.setCompleted(isCompleted != 0);
        toDoItem.setIdList(Integer.valueOf(idListString));

        return toDoItem;
    }
}

