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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import poker.fivecardstats.R;
import poker.fivecardstats.adapter.ScoreAdapter;
import poker.fivecardstats.dao.ScoreDataSource;
import poker.fivecardstats.model.Score;

public class ScoreActivity extends AppCompatActivity {
    private ScoreDataSource mScoreDataSource;
    private FloatingActionButton fab;
    private List<Score> mScores;
    private ScoreAdapter mAdapter;
    private ListView listView;
    private long id;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent user = getIntent();
        id = user.getLongExtra("id", 1L);

        init();
        initDB();
        listViewManager();
        fabManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void listViewManager() {
        mScores = mScoreDataSource.getScores(id);
        mAdapter = new ScoreAdapter(this, mScores);
        listView.setAdapter(mAdapter);
    }

    private void init() {
        listView = (ListView) findViewById(R.id.scores_listview);
    }

    private void initDB() {
        mScoreDataSource = new ScoreDataSource(this);
        try {
            mScoreDataSource.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fabManager() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(ScoreActivity.this);

                final View inflator = layoutInflater.inflate(R.layout.content_add_score, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
                builder.setTitle("Add new result");
                builder.setView(inflator);

                final HashMap<String, Integer> cardPoints = new HashMap<>();
                cardPoints.put("Par", 10);
                cardPoints.put("Två par", 20);
                cardPoints.put("Triss", 30);
                cardPoints.put("Stege", 40);
                cardPoints.put("Färg", 50);
                cardPoints.put("Kåk", 60);
                cardPoints.put("Fyrtal", 70);
                cardPoints.put("Royal street flush", 500);

                spinner = (Spinner) inflator.findViewById(R.id.card_points_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ScoreActivity.this,
                        R.array.card_score_array, android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = spinner.getSelectedItem().toString();
                        int score = cardPoints.get(type);
                        mScoreDataSource.create(id, type, score);

                        if(type.contains("Fyrtal")) {
                            mScoreDataSource.delete(id);
                        }
                        
                        updateList();
                    }
                }).show();
            }
        });
    }

    private void updateList() {
        mAdapter.clear();

        mScores = mScoreDataSource.getScores(id);
        mAdapter = new ScoreAdapter(this, mScores);
        listView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
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
