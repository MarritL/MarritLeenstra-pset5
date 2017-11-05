package marrit.marritleenstra_pset5;

/**
 * Created by Marrit on 12-10-2017.
 * Class that holds all the information about a TO-DO item.
 */

public class ToDoItem {

    // declare variables of ToDoItem class
    private int mId;
    private String mTitle;
    private Boolean mCompleted;

    // initiate instance of Class always not completed
    ToDoItem() {
        mCompleted = false;
    }

    // initiate instance of class with given ID (already in database)
    public ToDoItem(int id) {
        mId = id;
    }

    // getters and setters for all fields
    public int getId() {
        return mId;
    }

    String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    Boolean getCompleted() {
        return mCompleted;
    }

    public void setCompleted(Boolean completed) {
        mCompleted = completed;
    }
}
