package com.nammahasiru.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nammahasiru.data.Plant
import com.nammahasiru.data.PlantDatabase
import com.nammahasiru.data.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// AndroidViewModel instead of ViewModel because we need application context for db
class PlantViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: PlantRepository
    val allPlants: LiveData<List<Plant>>
    val totalCount: LiveData<Int>
    val aliveCount: LiveData<Int>

    init {
        val db = PlantDatabase.getDatabase(application)
        repo = PlantRepository(db.plantDao())
        allPlants = repo.allPlants
        totalCount = repo.totalPlants
        aliveCount = repo.alivePlants
    }

    fun addNewPlant(plant: Plant) {
        // using IO dispatcher because database operations should not run on main thread
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(plant)
        }
    }

    fun updateExistingPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(plant)
        }
    }

    fun removePlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(plant)
        }
    }

    fun getPlantsForCheckup(currentTime: Long) = repo.getPlantsForCheckup(currentTime)

    fun calculateSurvival(total: Int, alive: Int): Int {
        return repo.getSurvivalPercent(total, alive)
    }
}
