package marrit.marritleenstra_pset5;

import java.util.List;

/**
 * Created by Marrit on 5-11-2017.
 */

public class ToDoList {

    // declare variables of ToDoList class
    private int mID;
    private String mTitle;

    // initiate empty instance of Class
    public ToDoList() {
    }

    // initiate instance of class with given ID (already in database)
    public ToDoList(int id) {
        mID = id;
    }

    // getters
    public int getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
