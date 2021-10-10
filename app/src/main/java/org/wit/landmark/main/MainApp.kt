package org.wit.landmark.main

import android.app.Application
import org.wit.landmark.models.LandmarkMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val landmarks = LandmarkMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Landmark started")
//        landmarks.add(LandmarkModel("One", "About one..."))
//        landmarks.add(LandmarkModel("Two", "About two..."))
//        landmarks.add(LandmarkModel("Three", "About three..."))
    }
}