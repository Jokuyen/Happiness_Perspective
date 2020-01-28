package com.example.happinessperspective.progress

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

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
        viewModelFactory =
            ProgressViewModelFactory(
                dataSource,
                application
            )

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProgressViewModel::class.java)
        binding.setLifecycleOwner(this)

        // Set up chart
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            barChartView.data = viewModel.getBarChartData()
            barChartView.setVisibleXRangeMaximum(4f)
            barChartView.invalidate() // Refresh the chart
        })

        barChartFormatting()
    }

    private fun barChartFormatting() {
        // X-axis formatting
        val xAxis = barChartView.xAxis
        xAxis.valueFormatter = ChartXAxisFormatter()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setLabelCount(4)
        xAxis.setTextSize(18f)

        // Y-axis formatting
        val yAxis = barChartView.axisLeft
        yAxis.setTextSize(18f)

        // Remove unnecessary labels
        barChartView.getDescription().setEnabled(false)
        barChartView.getLegend().setEnabled(false)

        // Remove grid lines
        barChartView.xAxis.setDrawGridLines(false)
        barChartView.getAxisRight().setEnabled(false)

        // Remove gap between bars and labels
        barChartView.axisLeft.axisMinimum = 0f
        barChartView.axisRight.axisMinimum = 0f

        // Prevent bottom of x-axis labels from cutting off
        barChartView.setExtraOffsets(0f, 10f, 0f, 10f);
    }
}
