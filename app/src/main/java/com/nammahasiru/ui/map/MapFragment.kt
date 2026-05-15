package com.nammahasiru.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nammahasiru.R
import com.nammahasiru.ui.PlantViewModel

// map screen - shows all plants as markers on google maps
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantViewModel::class.java)

        // initialize the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // observe plants and add markers when data is available
        viewModel.allPlants.observe(viewLifecycleOwner) { plants ->
            googleMap.clear()   // remove old markers first

            if (plants.isEmpty()) return@observe

            for (plant in plants) {
                if (plant.lat == 0.0 && plant.lng == 0.0) continue  // skip if no gps data

                val position = LatLng(plant.lat, plant.lng)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(plant.name)
                        .snippet("Status: ${plant.currentStatus} | ${plant.location}")
                )
            }

            // move camera to first plant location
            val firstPlant = plants.firstOrNull { it.lat != 0.0 }
            if (firstPlant != null) {
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(firstPlant.lat, firstPlant.lng), 12f
                    )
                )
            }
        }
    }
}
