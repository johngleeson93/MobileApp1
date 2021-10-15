package org.wit.landmark.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class LandmarkMemStore : LandmarkStore {

    val landmarks = ArrayList<LandmarkModel>()

    override fun findAll(): List<LandmarkModel> {
        return landmarks
    }

    override fun create(landmark: LandmarkModel) {
        landmark.id = getId()
        landmarks.add(landmark)
        logAll()
    }

    override fun update(landmark: LandmarkModel) {
        var foundLandmark: LandmarkModel? = landmarks.find { p -> p.id == landmark.id }
        if (foundLandmark != null) {
            foundLandmark.title = landmark.title
            foundLandmark.description = landmark.description
            foundLandmark.image = landmark.image
            logAll()
        }
    }

    private fun logAll() {
        landmarks.forEach { i("$it") }
    }
}