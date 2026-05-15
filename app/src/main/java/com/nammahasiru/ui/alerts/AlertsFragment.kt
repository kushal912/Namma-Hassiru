package com.nammahasiru.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nammahasiru.R
import com.nammahasiru.ui.PlantViewModel
import com.nammahasiru.ui.home.PlantAdapter

// this screen shows plants that haven't been updated in 90+ days
class AlertsFragment : Fragment() {

    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantViewModel::class.java)

        val tvNoAlerts = view.findViewById<TextView>(R.id.tvNoAlerts)
        val rvAlerts = view.findViewById<RecyclerView>(R.id.rvAlerts)

        val adapter = PlantAdapter()
        rvAlerts.layoutManager = LinearLayoutManager(requireContext())
        rvAlerts.adapter = adapter

        // 90 days in ms
        val checkupThreshold = System.currentTimeMillis() - 7776000000L

        viewModel.getPlantsForCheckup(checkupThreshold).observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                tvNoAlerts.visibility = View.VISIBLE
                rvAlerts.visibility = View.GONE
            } else {
                tvNoAlerts.visibility = View.GONE
                rvAlerts.visibility = View.VISIBLE
                adapter.setPlants(list)
            }
        }
    }
}
