package com.example.progress

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.happinessperspective.database.EntryDatabase
import com.example.happinessperspective.databinding.ProgressFragmentBinding
import com.github.mikephil.charting.components.XAxis
import kotlinx.android.synthetic.main.progress_fragment.*

class ProgressFragment : Fragment() {

    private lateinit var binding: ProgressFragmentBinding
    private lateinit var viewModel: ProgressViewModel
    private lateinit var viewModelFactory: ProgressViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProgressFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        viewModelFactory = ProgressViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgressViewModel::class.java)
        binding.setLifecycleOwner(this)

        // Set up chart
        barChartView.xAxis.valueFormatter = chartXAxisFormatter()
        barChartView.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        barChartView.getDescription().setEnabled(false)
        barChartView.getLegend().setEnabled(false)

        // Remove grid lines
        barChartView.xAxis.setDrawGridLines(false)
        barChartView.getAxisLeft().setEnabled(false)
        barChartView.getAxisRight().setEnabled(false)

        // Disable touch interaction
        barChartView.setDragEnabled(false)
        barChartView.setPinchZoom(false)
        barChartView.setDoubleTapToZoomEnabled(false)
        barChartView.setHorizontalScrollBarEnabled(false)

        barChartView.data = viewModel.getBarChartData()
        barChartView.invalidate() // refresh the chart
    }

}
