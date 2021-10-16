package org.wit.landmark.activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import landmark.R
import landmark.databinding.ActivityLandmarkBinding
import org.wit.landmark.main.MainApp
import org.wit.landmark.models.Location
import org.wit.landmark.models.LandmarkModel
import org.wit.landmark.showImagePicker
import timber.log.Timber.i

class LandmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandmarkBinding
    var landmark = LandmarkModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityLandmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Landmark Activity started...")

        if (intent.hasExtra("landmark_edit")) {
            edit = true
            landmark = intent.extras?.getParcelable("landmark_edit")!!
            binding.landmarkTitle.setText(landmark.title)
            binding.description.setText(landmark.description)
            binding.btnAdd.setText(R.string.save_landmark)
            Picasso.get()
                .load(landmark.image)
                .into(binding.landmarkImage)
            if (landmark.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_landmark_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            landmark.title = binding.landmarkTitle.text.toString()
            landmark.description = binding.description.text.toString()
            if (landmark.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_landmark_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.landmarkStore.update(landmark.copy())
                } else {
                    app.landmarkStore.create(landmark.copy())
                }
            }
            i("add Button Pressed: $landmark")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.landmarkLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_landmark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            landmark.image = result.data!!.data!!
                            Picasso.get()
                                .load(landmark.image)
                                .into(binding.landmarkImage)
                            binding.chooseImage.setText(R.string.change_landmark_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> {
                    }
                    else -> {
                    }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> {
                    }
                    else -> {
                    }
                }
            }
    }
}