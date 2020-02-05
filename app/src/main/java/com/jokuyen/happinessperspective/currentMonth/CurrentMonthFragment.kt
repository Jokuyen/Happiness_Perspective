package com.jokuyen.happinessperspective.currentMonth

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jokuyen.happinessperspective.R
import com.jokuyen.happinessperspective.RecyclerViewAdapter

import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.CurrentMonthFragmentBinding

class CurrentMonthFragment : Fragment() {
    private lateinit var binding: CurrentMonthFragmentBinding
    private lateinit var viewModel: CurrentMonthViewModel
    private lateinit var viewModelFactory: CurrentMonthViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentMonthFragmentBinding.inflate(inflater)

        binding.recyclerView.adapter =
            RecyclerViewAdapter(
                RecyclerViewAdapter.OnClickListener {
                    // Navigate to entry's detail screen
                    this.findNavController().navigate(
                        CurrentMonthFragmentDirections.actionCurrentMonthFragmentToEntryDetails(it)
                    )
                })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        viewModelFactory = CurrentMonthViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentMonthViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

    }

    // Overflow menu methods
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.month_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.monthMenu -> {
                viewModel.onClearCurrentMonthAndYearButtonClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
