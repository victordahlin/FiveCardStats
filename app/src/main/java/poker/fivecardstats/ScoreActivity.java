package poker.fivecardstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import poker.fivecardstats.adapter.UsersAdapter;
import poker.fivecardstats.model.User;

public class ScoreActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mRecyclerView = (RecyclerView) findViewById(R.id.rUsers);

        // Improve layout size of the RecycleView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUserListItems();
    }

    /**
     * Change to Detail Activity with data (future..)
     * @param v detail view
     */
    public void onClick(View v) {
        Intent detailIntent = new Intent(ScoreActivity.this, DetailActivity.class);
        startActivity(detailIntent);
    }

    /**
     *  Apply dummy data to the adapter
     */
    private void setUserListItems() {
        ArrayList<User> users = new ArrayList<>();

        users.add(new User("Victor"));
        users.add(new User("Malin"));
        users.add(new User("Ulla"));

        mAdapter = new UsersAdapter(users);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
