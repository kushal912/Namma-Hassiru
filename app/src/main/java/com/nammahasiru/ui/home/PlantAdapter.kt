package com.nammahasiru.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nammahasiru.R
import com.nammahasiru.data.Plant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// simple adapter for showing plant list on home screen
class PlantAdapter : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    private var plantList = listOf<Plant>()

    // call this to update the list
    fun setPlants(newList: List<Plant>) {
        plantList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant_card, parent, false)
        return PlantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentPlant = plantList[position]
        holder.bind(currentPlant)
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvPlantName)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvPlantLocation)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvPlantStatus)
        private val tvDate: TextView = itemView.findViewById(R.id.tvPlantDate)

        fun bind(plant: Plant) {
            tvName.text = plant.name
            tvLocation.text = plant.location

            // format the date nicely
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val dateStr = sdf.format(Date(plant.datePlanted))
            tvDate.text = "Planted on: $dateStr"

            // show status with small indicator
            when (plant.currentStatus) {
                "alive" -> {
                    tvStatus.text = "Alive"
                    tvStatus.setTextColor(itemView.context.getColor(R.color.statusAlive))
                }
                "dead" -> {
                    tvStatus.text = "Did not survive"
                    tvStatus.setTextColor(itemView.context.getColor(R.color.statusDead))
                }
                else -> {
                    tvStatus.text = "Not updated yet"
                    tvStatus.setTextColor(itemView.context.getColor(R.color.statusUnknown))
                }
            }
        }
    }
}
