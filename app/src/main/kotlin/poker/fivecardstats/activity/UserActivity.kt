package poker.fivecardstats.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import poker.fivecardstats.R
import poker.fivecardstats.dao.UserDataSource

class UserActivity : AppCompatActivity() {
    private var mId: Long = 0
    private var mName: String? = null
    private lateinit var mUserDataSource: UserDataSource
    private lateinit var etName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val user = intent
        mId = user.getLongExtra("id", 1L)
        mName = user.getStringExtra("name")

        etName = findViewById(R.id.name_edittext)
        etName.setText(mName)

        initDB()

        scoreActivityWithIntentData()
        saveButtonOnClick()
        deleteButtonOnClick()
    }

    private fun initDB() {
        mUserDataSource = UserDataSource(this)
        try {
            mUserDataSource.open()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun scoreActivityWithIntentData() {
        val result = findViewById<Button>(R.id.scores_button)
        result.setOnClickListener { 
            val userIntent = Intent(this@UserActivity, ScoreActivity::class.java)
            userIntent.putExtra("id", mId)
            userIntent.putExtra("name", mName)
            startActivity(userIntent)
        }
    }

    private fun saveButtonOnClick() {
        val save = findViewById<Button>(R.id.save_button)
        save.setOnClickListener { v ->
            val newName = etName.text.toString()
            mUserDataSource.update(newName, mId)

            Snackbar.make(v,
                    resources.getString(R.string.user_name_update), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun deleteButtonOnClick() {
        val delete = findViewById<Button>(R.id.delete_player_button)
        delete.setOnClickListener { 
            mUserDataSource.delete(mId)
            finish()
        }
    }

}