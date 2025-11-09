package poker.fivecardstats.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import poker.fivecardstats.R
import poker.fivecardstats.adapter.ScoreAdapter
import poker.fivecardstats.dao.ScoreDataSource
import poker.fivecardstats.model.Score

class ScoreActivity : AppCompatActivity() {
    private lateinit var mScoreDataSource: ScoreDataSource
    private lateinit var fab: FloatingActionButton
    private lateinit var mScores: List<Score>
    private lateinit var mAdapter: ScoreAdapter
    private lateinit var listView: ListView
    private lateinit var totalScoreTextView: TextView
    private var id: Long = 0
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val user = intent
        id = user.getLongExtra("id", 1L)
        val name = user.getStringExtra("name")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = name

        init()
        initDB()
        listViewManager()
        fabManager()
    }

    private fun listViewManager() {
        mScores = mScoreDataSource.getScores(id)
        mAdapter = ScoreAdapter(this, mScores)
        listView.adapter = mAdapter
        updateTotalScore()
    }

    private fun init() {
        listView = findViewById(R.id.scores_listview)
        totalScoreTextView = findViewById(R.id.total_score_textview)
    }

    private fun initDB() {
        mScoreDataSource = ScoreDataSource(this)
        try {
            mScoreDataSource.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun fabManager() {
        fab = findViewById(R.id.fab)
        fab.setOnClickListener { 
            val layoutInflater = LayoutInflater.from(this@ScoreActivity)

            val inflator = layoutInflater.inflate(R.layout.content_add_score, null)

            val builder = AlertDialog.Builder(this@ScoreActivity)
            builder.setTitle("Add new result")
            builder.setView(inflator)

            val cardPoints = hashMapOf(
                    "Par" to 10,
                    "Två par" to 20,
                    "Triss" to 30,
                    "Stege" to 40,
                    "Färg" to 50,
                    "Kåk" to 60,
                    "Fyrtal" to 70,
                    "Royal street flush" to 500
            )

            spinner = inflator.findViewById(R.id.card_points_spinner)
            val adapter = ArrayAdapter.createFromResource(this@ScoreActivity,
                    R.array.card_score_array, android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            builder.setPositiveButton("Submit") { _, _ ->
                val type = spinner.selectedItem.toString()
                val score = cardPoints[type] ?: 0
                mScoreDataSource.create(id, type, score)

                if (type.contains("Fyrtal")) {
                    mScoreDataSource.delete(id)
                }

                updateList()
            }.show()
        }
    }

    private fun updateList() {
        mScores = mScoreDataSource.getScores(id)
        mAdapter.clear()
        mAdapter.addAll(mScores)
        updateTotalScore()
    }

    private fun updateTotalScore() {
        val totalScore = mScores.sumOf { it.score }
        totalScoreTextView.text = "Total: ${totalScore}p"
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return false
        }
        return super.onOptionsItemSelected(item)
    }
}