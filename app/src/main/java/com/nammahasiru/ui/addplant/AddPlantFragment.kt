package com.nammahasiru.ui.addplant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nammahasiru.R
import com.nammahasiru.data.Plant
import com.nammahasiru.ui.PlantViewModel
import com.nammahasiru.utils.ReminderWorker
import java.util.concurrent.TimeUnit

class AddPlantFragment : Fragment() {

    private lateinit var viewModel: PlantViewModel
    private lateinit var locationClient: FusedLocationProviderClient

    // store gps coords here after fetching
    private var userLat = 0.0
    private var userLng = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantViewModel::class.java)
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val etPlantName = view.findViewById<EditText>(R.id.etPlantName)
        val etAreaName = view.findViewById<EditText>(R.id.etAreaName)
        val etNotes = view.findViewById<EditText>(R.id.etNotes)
        val tvGpsCoords = view.findViewById<TextView>(R.id.tvGpsCoords)
        val btnGetLocation = view.findViewById<Button>(R.id.btnGetLocation)
        val btnSave = view.findViewById<Button>(R.id.btnSavePlant)

        btnGetLocation.setOnClickListener {
            getLocation(tvGpsCoords)
        }

        btnSave.setOnClickListener {
            val plantName = etPlantName.text.toString().trim()
            val areaName = etAreaName.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            // basic validation
            if (plantName.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter plant name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userLat == 0.0) {
                Toast.makeText(requireContext(), "Please get GPS location first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPlant = Plant(
                name = plantName,
                location = areaName,
                lat = userLat,
                lng = userLng,
                datePlanted = System.currentTimeMillis(),
                currentStatus = "unknown",
                lastUpdated = System.currentTimeMillis(),
                notes = notes
            )

            viewModel.addNewPlant(newPlant)

            // schedule notification after 90 days using WorkManager
            scheduleReminder(plantName)

            Toast.makeText(requireContext(), "Plant saved successfully!", Toast.LENGTH_SHORT).show()

            // clear fields
            etPlantName.text.clear()
            etAreaName.text.clear()
            etNotes.text.clear()
            tvGpsCoords.text = "GPS: not fetched yet"
            userLat = 0.0
            userLng = 0.0
        }
    }

    private fun getLocation(tvGps: TextView) {
        // check if location permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // ask for permission
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }

        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                userLat = location.latitude
                userLng = location.longitude
                tvGps.text = "GPS: $userLat, $userLng"
            } else {
                Toast.makeText(requireContext(), "Could not get location, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scheduleReminder(plantName: String) {
        val data = workDataOf("plant_name" to plantName)

        val reminderWork = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(90, TimeUnit.DAYS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(reminderWork)
    }

    // handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission granted, tap Get Location again", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
