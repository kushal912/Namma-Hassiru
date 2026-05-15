package com.nammahasiru.data

import androidx.lifecycle.LiveData

// Repository acts as middle layer between ViewModel and Database
// not 100% sure why we need this but the tutorial said its good practice
// something about keeping viewmodel clean
class PlantRepository(private val dao: PlantDao) {

    val allPlants: LiveData<List<Plant>> = dao.getAllPlants()
    val totalPlants: LiveData<Int> = dao.getTotalCount()
    val alivePlants: LiveData<Int> = dao.getAliveCount()

    fun getPlantsForCheckup(time: Long) = dao.getPlantsNeedCheckup(time)

    suspend fun insert(plant: Plant) {
        dao.addPlant(plant)
    }

    suspend fun update(plant: Plant) {
        dao.updatePlant(plant)
    }

    suspend fun delete(plant: Plant) {
        dao.deletePlant(plant)
    }

    suspend fun findById(id: Int): Plant? {
        return dao.getPlantById(id)
    }

    // calculate survival rate as percentage
    fun getSurvivalPercent(total: Int, alive: Int): Int {
        if (total == 0) return 0
        val percent = (alive.toFloat() / total.toFloat()) * 100
        return percent.toInt()
    }
}
