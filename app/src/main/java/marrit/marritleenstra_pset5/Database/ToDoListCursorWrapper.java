package marrit.marritleenstra_pset5.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import marrit.marritleenstra_pset5.ToDoList;

/**
 * Created by Marrit on 8-11-2017.
 * A CursorWrapper to pull out data of the database.
 * Based on: Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch guide.
 * 3th edition. Chapter 14.
 */

public class ToDoListCursorWrapper extends CursorWrapper {

    //constructor
    public ToDoListCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDoList getToDoListItem() {
        // pull the data out of the database
        String idString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols._id));
        String title = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols.TITLE));

        // put the data in a ToDoList Item
        ToDoList toDoList = new ToDoList(Integer.valueOf(idString));
        toDoList.setTitle(title);

        return toDoList;
    }

}