package com.jokuyen.happinessperspective.settings

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.SettingsFragmentBinding
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var viewModelFactory: SettingsViewModelFactory
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        viewModelFactory = SettingsViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.viewModel = viewModel

        // Setup year spinner
        viewModel.yearsArray.observe(viewLifecycleOwner, Observer {
            val adapter: ArrayAdapter<Any>

            // If year array is empty, use the default year to fill spinner
            if (it.isNotEmpty()) {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
            } else {
                val arr = arrayOf(CurrentYearSingleton.currentYear)
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arr)
            }

            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

            current_year_spinner.adapter = adapter
            current_year_spinner.onItemSelectedListener = SpinnerAdapterListener()

            // Select default value of spinner
            val yearPosition = adapter.getPosition(CurrentYearSingleton.currentYear)
            current_year_spinner.setSelection(yearPosition)
        })
    }

}
