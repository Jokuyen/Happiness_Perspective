package com.jokuyen.happinessperspective.currentMonth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.CurrentMonthDetailsFragmentBinding

class CurrentMonthDetailsFragment : Fragment() {

    private lateinit var binding: CurrentMonthDetailsFragmentBinding
    private lateinit var viewModel: CurrentMonthDetailsViewModel
    private lateinit var viewModelFactory: CurrentMonthDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentMonthDetailsFragmentBinding.inflate(inflater)

        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            // Navigate to entry's detail screen
            this.findNavController().navigate(
                CurrentMonthDetailsFragmentDirections.actionCurrentMonthDetailsFragmentToEntryDetails(it))
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        viewModelFactory = CurrentMonthDetailsViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentMonthDetailsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

    }

}
