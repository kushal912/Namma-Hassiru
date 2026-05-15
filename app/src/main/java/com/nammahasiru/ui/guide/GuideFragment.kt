package com.nammahasiru.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nammahasiru.R

// Species guide screen
// TODO: later can make this dynamic based on actual survival data from db
// for now hardcoding some common species for Bangalore region
class GuideFragment : Fragment() {

    // using a data class to hold species info
    data class SpeciesInfo(
        val name: String,
        val localName: String,
        val survivalRate: Int,  // percentage
        val tip: String
    )

    // common trees that grow well in karnataka
    private val speciesList = listOf(
        SpeciesInfo("Neem", "Bevu", 88, "Very hardy tree, grows well in dry climate. Needs minimal care after first 3 months."),
        SpeciesInfo("Peepal", "Ashwatha", 75, "Sacred tree, grows almost anywhere. Good for roadside plantation."),
        SpeciesInfo("Banyan", "Ala Mara", 80, "Needs open space to grow. Very long lifespan, great for parks."),
        SpeciesInfo("Mango", "Maavin Mara", 60, "Needs regular watering for first year. Best planted in monsoon season."),
        SpeciesInfo("Coconut", "Tenginakai", 70, "Grows well in coastal areas of Karnataka. Needs good drainage."),
        SpeciesInfo("Tulsi", "Tulsi", 92, "Easy to grow anywhere. Very low maintenance plant.")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<LinearLayout>(R.id.speciesContainer)

        // creating cards dynamically for each species
        // could use recyclerview but this works for small list
        for (species in speciesList) {
            val cardView = layoutInflater.inflate(R.layout.item_species_card, container, false)

            cardView.findViewById<TextView>(R.id.tvSpeciesName).text = species.name
            cardView.findViewById<TextView>(R.id.tvLocalName).text = "(${species.localName})"
            cardView.findViewById<TextView>(R.id.tvSpeciesTip).text = species.tip
            cardView.findViewById<TextView>(R.id.tvSurvivalPct).text = "${species.survivalRate}%"
            cardView.findViewById<ProgressBar>(R.id.pbSurvival).progress = species.survivalRate

            container.addView(cardView)
        }
    }
}
