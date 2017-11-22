package marrit.marritleenstra_pset5;


/**
 * Created by Marrit on 5-11-2017.
 * Class that holds all the information about a TO-DOList item.
 */

public class ToDoList {

    // declare variables of ToDoList class
    private int mID;
    private String mTitle;

    // initiate instance of class not yet in database
    ToDoList(String title) {
        mTitle = title;
    }

    // initiate instance of class with given ID (already in database)
    public ToDoList(int id) {
        mID = id;
    }

    // getters
    int getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
