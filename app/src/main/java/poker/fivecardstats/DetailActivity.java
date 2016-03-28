package poker.fivecardstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import poker.fivecardstats.adapter.DetailsAdapter;
import poker.fivecardstats.model.Detail;

public class DetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.r_details);

        // Improve layout size of the RecycleView
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUserListItems();
    }

    /**
     * Add dummy data to the adapter and
     * the total points
     */
    private void setUserListItems() {
        ArrayList<Detail> details = new ArrayList<>();

        details.add(new Detail("Färg", 50));
        details.add(new Detail("Färg", 50));
        details.add(new Detail("Spel", 50));

        int mTotalPoints = 0;
        for(Detail d : details) {
            mTotalPoints += d.getPoint();
        }

        mAdapter = new DetailsAdapter(details);
        mRecyclerView.setAdapter(mAdapter);

        TextView mTvTotalPoints = (TextView) findViewById(R.id.tv_total_points);
        mTvTotalPoints.setText("Totalt: " + mTotalPoints);
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
