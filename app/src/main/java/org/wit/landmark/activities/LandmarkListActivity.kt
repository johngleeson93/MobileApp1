package org.wit.landmark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.landmark.R
import org.wit.landmark.adapters.LandmarkAdapter
import org.wit.landmark.adapters.LandmarkListener
import org.wit.landmark.databinding.ActivityLandmarkListBinding
import org.wit.landmark.main.MainApp
import org.wit.landmark.models.LandmarkModel

class LandmarkListActivity : AppCompatActivity(), LandmarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityLandmarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = LandmarkAdapter(app.landmarks.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, LandmarkActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLandmarkClick(landmark: LandmarkModel) {
        val launcherIntent = Intent(this, LandmarkActivity::class.java)
        launcherIntent.putExtra("landmark_edit", landmark)
        startActivityForResult(launcherIntent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}