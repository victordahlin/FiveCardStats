package poker.fivecardstats.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import poker.fivecardstats.R
import poker.fivecardstats.adapter.UsersAdapter
import poker.fivecardstats.dao.UserDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var mUserDataSource: UserDataSource
    private lateinit var listView: ListView
    private lateinit var mAdapter: UsersAdapter
    private var TOTAL_PLAYERS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        initDB()
        setupListView() // Setup UI with an empty list
        fabManager()
        clearButtonOnClick()
    }

    private fun clearButtonOnClick() {
        val clear = findViewById<Button>(R.id.clear_button)
        clear.setOnClickListener {
            mUserDataSource.resetAllScores()
            updateList() // Refresh the list to show updated scores
        }
    }

    private fun fabManager() {
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val layoutInflater = LayoutInflater.from(this@MainActivity)
            val inflator = layoutInflater.inflate(R.layout.content_add_player, null)

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Add new person")
            builder.setView(inflator)

            val etName = inflator.findViewById<EditText>(R.id.et_name)

            builder.setPositiveButton("Submit") { _, _ ->
                var name = etName.text.toString()

                if (name.isEmpty()) {
                    name = "Player$TOTAL_PLAYERS"
                    TOTAL_PLAYERS++
                }

                mUserDataSource.create(name)
                updateList() // Refresh the list to show the new user
            }.show()
        }
    }

    private fun setupListView() {
        listView = findViewById(R.id.users_listview)
        mAdapter = UsersAdapter(this, arrayListOf()) // Start with an empty list
        listView.adapter = mAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val user = mAdapter.getItem(position) ?: return@setOnItemClickListener

            val userIntent = Intent(this@MainActivity, UserActivity::class.java).apply {
                putExtra("id", user.id)
                putExtra("name", user.name)
            }
            startActivity(userIntent)
        }
    }

    private fun initDB() {
        mUserDataSource = UserDataSource(this)
        try {
            mUserDataSource.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateList() {
        val users = mUserDataSource.users
        mAdapter.clear()
        mAdapter.addAll(users)
    }

    public override fun onResume() {
        super.onResume()
        updateList() // Load data when the activity becomes visible
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            false
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}