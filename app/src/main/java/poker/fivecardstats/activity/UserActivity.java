package poker.fivecardstats.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import poker.fivecardstats.R;
import poker.fivecardstats.dao.UserDataSource;

public class UserActivity extends AppCompatActivity {
    private long mId;
    private String mName;
    private UserDataSource mUserDataSource;
    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent user = getIntent();
        mId = user.getLongExtra("id", 1L);
        mName = user.getStringExtra("name");

        etName = (EditText) findViewById(R.id.name_edittext);
        etName.setText(mName);

        initDB();

        scoreActivityWithIntentData();
        saveButtonOnClick();
        deleteButtonOnClick();
    }

    private void initDB() {
        mUserDataSource = new UserDataSource(this);
        try {
            mUserDataSource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scoreActivityWithIntentData() {
        Button result = (Button) findViewById(R.id.scores_button);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(UserActivity.this, ScoreActivity.class);
                userIntent.putExtra("id", mId);
                userIntent.putExtra("name", mName);
                startActivity(userIntent);
            }
        });
    }

    private void saveButtonOnClick() {
        Button save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = etName.getText().toString();
                mUserDataSource.update(newName, mId);

                Snackbar.make(v,
                        getResources().getString(R.string.user_name_update), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void deleteButtonOnClick() {
        Button delete = (Button) findViewById(R.id.delete_player_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataSource.delete(mId);
                finish();
            }
        });
    }

}
