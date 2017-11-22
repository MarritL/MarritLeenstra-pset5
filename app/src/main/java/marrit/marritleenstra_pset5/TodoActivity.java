package marrit.marritleenstra_pset5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends AppCompatActivity {

    // add variables
    private ToDoItem mToDo;
    private EditText mNewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        // display the right todoItem
        Intent intent = getIntent();
        int mToDoId = intent.getIntExtra("Extra_TODOID", 0);
        mToDo = ToDoManager.getInstance(TodoActivity.this).getToDoItem(mToDoId);

        TextView title = (TextView) findViewById(R.id.the_title);
        title.setText(mToDo.getTitle());

        mNewTitle = (EditText) findViewById(R.id.ET_change_title);

        // initialise OK button and enable clicks on OK button
        Button buttonOK = (Button) findViewById(R.id.Button_OK_title);
        buttonOK.setOnClickListener(new ButtonClickListener());
    }


    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String mNewName = mNewTitle.getText().toString();

            // check if new title is filled in
            if (!mNewName.equals("")) {
                mToDo.setTitle(mNewName);

                // change To-Do also in Database
                ToDoManager.getInstance(TodoActivity.this).updateToDoItem(mToDo);

                // get back to the todoList
                Intent intent = new Intent(view.getContext(), TodoListActivity.class);
                intent.putExtra("Extra_ListID", mToDo.getIdList());
                view.getContext().startActivity(intent);
            }

            else {
                // if user gave no title, yell at him
                Toast.makeText(TodoActivity.this, "Give a new title!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
