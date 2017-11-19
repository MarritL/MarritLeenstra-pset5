package marrit.marritleenstra_pset5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {

    private ToDoItem mToDo;
    private TextView mTitle;
    private EditText mNewTitle;
    private Button mButtonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        int mToDoId = getIntent().getIntExtra("Extra_TODOID", 0);
        mToDo = ToDoManager.getInstance(TodoActivity.this).getToDoItem(mToDoId);

        mTitle = (TextView) findViewById(R.id.the_title);
        mTitle.setText(mToDo.getTitle());

        mNewTitle = (EditText) findViewById(R.id.ET_change_title);
        mButtonOK = (Button) findViewById(R.id.Button_OK_title);
        mButtonOK.setOnClickListener(new ButtonClickListener());

    }


    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void OnClick(View view) {

            String mNEWTITLE = mNewTitle.getText().toString();

            // check if new title is filled in
            if (!mNEWTITLE.equals("")) {
                mToDo.setTitle(mNEWTITLE);
                // TODO change in Database
            }

        }

    }
}
