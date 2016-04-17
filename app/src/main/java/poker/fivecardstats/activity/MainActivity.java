package poker.fivecardstats.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import poker.fivecardstats.R;
import poker.fivecardstats.adapter.UsersAdapter;
import poker.fivecardstats.dao.UserDataSource;
import poker.fivecardstats.model.User;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private UserDataSource mUserDataSource;
    private ListView listView;
    private List<User> mUsers;
    private  UsersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();
        init();
        listViewManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabManager();
    }

    private void fabManager() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

                final View inflator = layoutInflater.inflate(R.layout.content_add_player, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add new person");
                builder.setView(inflator);

                final EditText etName = (EditText) inflator.findViewById(R.id.et_name);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString();

                        mUserDataSource.create(name, 0);
                        updateList();
                    }
                }).show();
            }
        });
    }

    private void listViewManager() {
        mUsers = mUserDataSource.getUsers();
        mAdapter = new UsersAdapter(this, mUsers);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = mUsers.get(position);

                Intent score = new Intent(MainActivity.this, ScoreActivity.class);
                score.putExtra("id", u.getId());
                startActivity(score);
            }
        });
    }

    private void init() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.users_listview);
    }

    private void initDB() {
        mUserDataSource = new UserDataSource(this);
        try {
            mUserDataSource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateList() {
        mAdapter.clear();

        mUsers = mUserDataSource.getUsers();
        mAdapter = new UsersAdapter(this, mUsers);
        listView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
