package marrit.marritleenstra_pset5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ManyListsActivity extends AppCompatActivity {

    // add variables
    private RecyclerView mListsRecyclerView;
    private ToDoListAdapter mListsAdapter;
    private List<ToDoList> toDoListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_many_lists);

            // set up recyclerView
            mListsRecyclerView = (RecyclerView) findViewById(R.id.lists_recycler_view);
            mListsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // display recyclerView
            updateUI();

            // make swipe possible
            ItemTouchHelper();

            // initiate button to add a new To-Do
            FloatingActionButton mFloatingButtonAdd = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            mFloatingButtonAdd.setOnClickListener(new AddListClickListener());
        }

        // ViewHolder for the recyclerView
        private class ListsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView mListTitleTextView;
            private ToDoList mToDoListItem;

            // construct a viewHolder with the title textview
            ListsHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_list, parent, false));
                itemView.setOnClickListener(this);

                mListTitleTextView = (TextView) itemView.findViewById(R.id.list_title);
            }

            // bind the data to the viewHolder
            void bind(ToDoList toDoList) {
                mToDoListItem = toDoList;
                mListTitleTextView.setText(mToDoListItem.getTitle());
            }

            // set on click listener on the items of the list
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(view.getContext(), TodoListActivity.class);
                intent.putExtra("Extra_ListID", mToDoListItem.getID());

                view.getContext().startActivity(intent);
            }

        }

    // Adapter for the recyclerView
    private class ToDoListAdapter extends RecyclerView.Adapter<ListsHolder> {
        private List<ToDoList> mToDoListItems;

        ToDoListAdapter(List<ToDoList> todoListItems) {
            mToDoListItems = todoListItems;
        }

        // called when new viewHolder is needed
        @Override
        public ListsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new ListsHolder(layoutInflater, parent);
        }

        // call bind when a viewHolder needs to display a ToDoItem
        @Override
        public void onBindViewHolder(ListsHolder holder, int position) {
            ToDoList toDoListItem = mToDoListItems.get(position);
            holder.bind(toDoListItem);
        }


        @Override
        public int getItemCount() {
            return mToDoListItems.size();
        }

        // method to let the adapter know which list to use
        void setToDoListItems(List<ToDoList> toDoListItems) {
            mToDoListItems = toDoListItems;
        }
    }

        // listener to add new ToDoList
        private class AddListClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {

                // just to be sure
                if (view == findViewById(R.id.floatingActionButton)) {

                    // function in which the action takes place
                    showAddListDialog();
                }
            }
        }

        // show a dialog which allows user to add a new todolist
        private void showAddListDialog() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_add_list,null);
            dialogBuilder.setView(dialogView);

            final EditText mAddList = (EditText) dialogView.findViewById(R.id.EditText_add_list);

            // Let the user know what the dialog is for
            dialogBuilder.setMessage("Add new TODO list");

            // OK-button
            dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {

                    String mListTitle = mAddList.getText().toString();

                    // check if user gave To-Do title
                    if (!mListTitle.equals("")) {

                        // make To-Do List
                        ToDoList toDoList = new ToDoList(mListTitle);
                        ToDoManager.getInstance(ManyListsActivity.this).addToDoList(toDoList);

                        // display To-Do item
                        updateUI();

                        // empty edit-text
                        mAddList.getText().clear();

                    } else {
                        // if user gave no title, yell at him
                        Toast.makeText(ManyListsActivity.this, "Give the list a title!", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            // cancel button
            dialogBuilder.setNegativeButton("Cancel", new CancelListener());

            // when the building is done show the dialog in the app screen
            AlertDialog addList = dialogBuilder.create();
            addList.show();
        }



    // cancel listener for dialog box
    private class CancelListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int whichButton) {
            // do nothing and go back
        }
    }


    // update recyclerView
    private void updateUI() {
        ToDoManager toDoManager = ToDoManager.getInstance(this);
        toDoListItems = toDoManager.getToDoListItems();

        if (mListsAdapter == null) {
            mListsAdapter = new ToDoListAdapter(toDoListItems);
            mListsRecyclerView.setAdapter(mListsAdapter);
        } else {
            mListsAdapter.setToDoListItems(toDoListItems);
            mListsAdapter.notifyItemInserted(toDoListItems.size() - 1);
        }
    }

    // add swipe ability
    // source: https://stackoverflow.com/questions/34735297/recyclerview-and-itemtouchhelper-swipe-to-remove-issue
    private void ItemTouchHelper() {

        ToDoManager toDoManager = ToDoManager.getInstance(this);
        toDoListItems = toDoManager.getToDoListItems();

        // only swipe to left
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // disable onMove
            @Override
            public boolean onMove(RecyclerView recyclerview, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // enable swipe and delete swiped item from list and database
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                ToDoList toDoList = toDoListItems.get(swipedPosition);
                ToDoManager.getInstance(ManyListsActivity.this).deleteToDoList(toDoList);
                toDoListItems.remove(swipedPosition);
                mListsAdapter.setToDoListItems(toDoListItems);
                mListsAdapter.notifyItemRemoved(swipedPosition);
            }
        };

        // attach this ability to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mListsRecyclerView);
    }

}



