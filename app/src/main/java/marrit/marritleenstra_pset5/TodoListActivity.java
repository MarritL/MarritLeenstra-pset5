package marrit.marritleenstra_pset5;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    // add variables
    private RecyclerView mToDoRecyclerView;
    private ToDoAdapter mAdapter;
    private List<ToDoItem> toDoItems;
    private Integer mIdList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // pull right todolist out of intent
        mIdList = getIntent().getIntExtra("Extra_ListID", 0);
        System.out.println("AFTER getIntent " + mIdList);

        // set up recyclerView
        mToDoRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler_view);
        mToDoRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // display recyclerView
        updateUI();

        // make swipe possible
        ItemTouchHelper();

        // initiate button to add a new To-Do
        Button mButtonAdd = (Button) findViewById(R.id.Button_add);
        mButtonAdd.setOnClickListener(new AddOnClickListener());
    }


    // ViewHolder for the recyclerView
    private class ToDoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private ToDoItem mToDoItem;
        private CheckBox mCBToDoItem;

        // construct a viewHolder with the checkbox and textview
        ToDoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.todo_title);
            mCBToDoItem = (CheckBox) itemView.findViewById(R.id.CB_item);
            mCBToDoItem.setFocusable(false);
        }

        // bind the data to the viewHolder
        void bind(ToDoItem toDoItem) {
            mToDoItem = toDoItem;
            mTitleTextView.setText(mToDoItem.getTitle());

            // check and strike through only if the To-Do is completed
            if (mToDoItem.getCompleted()) {
                mCBToDoItem.setChecked(true);
                mTitleTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mCBToDoItem.setChecked(false);
            }

            // listen for a change in completed
            mCBToDoItem.setOnCheckedChangeListener(new MyCheckBoxListener());

        }

        // on long click listener to edit existing ToDos
        @Override
        public void onClick(View view) {

            Intent intent;
            intent = new Intent(view.getContext(), TodoActivity.class);
            intent.putExtra("Extra_TODOID", mToDoItem.getId());

            view.getContext().startActivity(intent);

            //return true;
        }

        // checkbox listener for the To-Dos
        class MyCheckBoxListener implements CompoundButton.OnCheckedChangeListener {

            @Override
            public void onCheckedChanged(CompoundButton checkbox, boolean isChecked) {
                if (isChecked) {
                    mToDoItem.setCompleted(true);
                    ToDoManager.getInstance(TodoListActivity.this).updateToDoItem(mToDoItem);
                    mTitleTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mToDoItem.setCompleted(false);
                    mTitleTextView.setPaintFlags(0);
                }
            }
        }


    }

    // Adapter for the recyclerView
    class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder> {
        private List<ToDoItem> mToDoItems;

        ToDoAdapter(List<ToDoItem> todoItems) {
            mToDoItems = todoItems;
        }

        // called when new viewHolder is needed
        @Override
        public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new ToDoHolder(layoutInflater, parent);
        }

        // call bind when a viewHolder needs to display a ToDoItem
        @Override
        public void onBindViewHolder(ToDoHolder holder, int position) {
            ToDoItem toDoItem = mToDoItems.get(position);
            holder.bind(toDoItem);
        }


        @Override
        public int getItemCount() {
            return mToDoItems.size();
        }

        // method to let the adapter know which list to use
        void setToDoItems(List<ToDoItem> toDoItems) {
            mToDoItems = toDoItems;
        }
    }

    // listener to add new To-Do
    private class AddOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == findViewById(R.id.Button_add)) {

                EditText mTitle = (EditText) findViewById(R.id.editText_add_title);

                // check if user gave To-Do title
                if (!mTitle.getText().toString().equals("")) {

                    // make To-Do item
                    ToDoItem toDoItem = new ToDoItem();
                    toDoItem.setTitle(mTitle.getText().toString());
                    toDoItem.setIdList(mIdList);
                    System.out.println("in Onclick, list number = " + mIdList);
                    ToDoManager.getInstance(TodoListActivity.this).addToDo(toDoItem);


                    // display To-Do item
                    updateUI();

                    // empty edit-text
                    mTitle.getText().clear();

                } else {
                    // if user gave no title, yell at him
                    Toast.makeText(TodoListActivity.this, "Give the TODO a title!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    // update recyclerView
    private void updateUI() {
        ToDoManager toDoManager = ToDoManager.getInstance(this);
        toDoItems = toDoManager.getToDoItems(mIdList);

        if (mAdapter == null) {
            mAdapter = new ToDoAdapter(toDoItems);
            mToDoRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setToDoItems(toDoItems);
            mAdapter.notifyItemInserted(toDoItems.size() - 1);
        }
    }

    // add swipe ability
    // source: https://stackoverflow.com/questions/34735297/recyclerview-and-itemtouchhelper-swipe-to-remove-issue
    private void ItemTouchHelper() {

        ToDoManager toDoManager = ToDoManager.getInstance(this);
        toDoItems = toDoManager.getToDoItems(mIdList);

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
                ToDoItem toDo = toDoItems.get(swipedPosition);
                ToDoManager.getInstance(TodoListActivity.this).deleteToDo(toDo);
                toDoItems.remove(swipedPosition);
                mAdapter.setToDoItems(toDoItems);
                mAdapter.notifyItemRemoved(swipedPosition);
            }
        };

        // attach this ability to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mToDoRecyclerView);
    }

    // on back pressed go always to ManyListActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(TodoListActivity.this, ManyListsActivity.class);
        startActivity(intent);
    }

}
