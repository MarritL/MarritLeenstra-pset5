package marrit.marritleenstra_pset5.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import marrit.marritleenstra_pset5.ToDoList;

/**
 * Created by Marrit on 8-11-2017.
 */

public class ToDoListCursorWrapper extends CursorWrapper {

    //constructor
    public ToDoListCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDoList getToDoListItem() {
        String idString = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_lists._id));
        String title = getString(getColumnIndex(ToDoDBSchema.ToDoTable.Cols_lists.TITLE));

        ToDoList toDoList = new ToDoList(Integer.valueOf(idString));
        toDoList.setTitle(title);

        return toDoList;
    }

}