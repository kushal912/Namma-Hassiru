package com.nammahasiru.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nammahasiru.R
import com.nammahasiru.ui.PlantViewModel

class HomeFragment : Fragment() {

    // declaring viewmodel here, initializing in onViewCreated
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PlantViewModel::class.java)

        // get views
        val tvTotalPlants = view.findViewById<TextView>(R.id.tvTotalPlants)
        val tvSurvivalRate = view.findViewById<TextView>(R.id.tvSurvivalRate)
        val tvCheckupNeeded = view.findViewById<TextView>(R.id.tvCheckupNeeded)
        val progressBar = view.findViewById<ProgressBar>(R.id.survivalProgress)
        val rvPlants = view.findViewById<RecyclerView>(R.id.rvPlantList)

        // setup recyclerview
        val adapter = PlantAdapter()
        rvPlants.layoutManager = LinearLayoutManager(requireContext())
        rvPlants.adapter = adapter

        // observe plant list
        viewModel.allPlants.observe(viewLifecycleOwner) { plantList ->
            adapter.setPlants(plantList)
        }

        // show total count
        viewModel.totalCount.observe(viewLifecycleOwner) { count ->
            tvTotalPlants.text = count.toString()
        }

        // calculate and show survival rate
        // need both total and alive count for this
        viewModel.aliveCount.observe(viewLifecycleOwner) { aliveNum ->
            val total = viewModel.totalCount.value ?: 0
            val survivalPct = viewModel.calculateSurvival(total, aliveNum)
            tvSurvivalRate.text = "$survivalPct%"
            progressBar.progress = survivalPct
        }

        // show how many plants need checkup
        // 90 days = 7776000000 milliseconds
        val ninetyDaysMs = 7776000000L
        val checkupTime = System.currentTimeMillis() - ninetyDaysMs
        viewModel.getPlantsForCheckup(checkupTime).observe(viewLifecycleOwner) { checkupList ->
            tvCheckupNeeded.text = "${checkupList.size} plants need check-up"
        }
    }
}
