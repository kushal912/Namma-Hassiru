package com.nammahasiru.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// this is our main plant model - stores all info about each plant
// learned about @Entity from youtube tutorial lol
@Entity(tableName = "plant_table")
data class Plant(

    @PrimaryKey(autoGenerate = true)
    val plantId: Int = 0,

    val name: String,           // species name like neem or peepal
    val location: String,       // area name the user types in
    val lat: Double = 0.0,
    val lng: Double = 0.0,

    val datePlanted: Long,      // storing as timestamp, easier to compare

    // status can be "alive", "dead" or "unknown"
    // using strings instead of enum because simpler
    var currentStatus: String = "unknown",

    var lastUpdated: Long = 0L,

    val photoUri: String = "",  // path to photo, empty if no photo taken

    val notes: String = ""
)
