package org.wit.landmark.main

import android.app.Application
import org.wit.landmark.models.LandmarkMemStore
import org.wit.landmark.models.LandmarkJSONStore
import org.wit.landmark.models.LandmarkStore
import timber.log.Timber

class MainApp : Application() {

    lateinit var landmarkStore: LandmarkJSONStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        landmarkStore = LandmarkJSONStore()
        Timber.i("Landmark Application Started")
    }
}