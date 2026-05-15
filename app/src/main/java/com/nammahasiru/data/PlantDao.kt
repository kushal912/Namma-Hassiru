package com.nammahasiru.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {

    @Insert
    suspend fun addPlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    // get all plants sorted newest first
    @Query("SELECT * FROM plant_table ORDER BY datePlanted DESC")
    fun getAllPlants(): LiveData<List<Plant>>

    // get plants where status is alive
    @Query("SELECT * FROM plant_table WHERE currentStatus = 'alive'")
    fun getAlivePlants(): LiveData<List<Plant>>

    // 90 days in milliseconds = 7776000000
    // this finds plants that haven't been checked in 90 days
    @Query("SELECT * FROM plant_table WHERE (:currentTime - lastUpdated) >= 7776000000 OR lastUpdated = 0")
    fun getPlantsNeedCheckup(currentTime: Long): LiveData<List<Plant>>

    @Query("SELECT COUNT(*) FROM plant_table")
    fun getTotalCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM plant_table WHERE currentStatus = 'alive'")
    fun getAliveCount(): LiveData<Int>

    // used this for survival % calculation on home screen
    @Query("SELECT * FROM plant_table WHERE plantId = :id")
    suspend fun getPlantById(id: Int): Plant?
}
