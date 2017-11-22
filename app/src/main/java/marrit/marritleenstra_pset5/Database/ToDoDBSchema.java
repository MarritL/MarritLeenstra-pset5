package marrit.marritleenstra_pset5.Database;

/**
 * Created by Marrit on 12-10-2017.
 * Class containing the schema for the database.
 * Based on: Phillips, Stewart, Marsicano (2017). Android Programming. The big nerd ranch guide.
 * 3th edition. Chapter 14.
 */

// schema for the database (easily changeable if needed)
public class ToDoDBSchema {
    public static final class ToDoTable {

        // table names
        public static final String TABLE_TODOS = "todos";
        public static final String TABLE_LISTS = "lists";

        // column names
        public static final class Cols {
            public static final String _id = "id";
            public static final String TITLE = "title";
            public static final String COMPLETED = "completed";
            public static final String id_list = "idList";
        }
    }
}
