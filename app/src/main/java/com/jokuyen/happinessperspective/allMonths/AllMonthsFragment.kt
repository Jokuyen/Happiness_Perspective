package com.jokuyen.happinessperspective.allMonths

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.R

import com.jokuyen.happinessperspective.RecyclerViewAdapter
import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.AllMonthsFragmentBinding

class AllMonthsFragment : Fragment() {
    private lateinit var binding: AllMonthsFragmentBinding
    private lateinit var viewModel: AllMonthsViewModel
    private lateinit var viewModelFactory : AllMonthsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllMonthsFragmentBinding.inflate(inflater)

        binding.recyclerView.adapter =
            RecyclerViewAdapter(
                RecyclerViewAdapter.OnClickListener {
                    // Navigate to entry's detail screen
                    this.findNavController().navigate(
                        AllMonthsFragmentDirections.actionAllMonthsFragmentToEntryDetailsFragment(it)
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
        viewModelFactory = AllMonthsViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(AllMonthsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        activity?.title = CurrentYearSingleton.currentYear.toString()
    }

    // Overflow menu methods
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_months_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.allMonthsMenu -> {
                viewModel.onClearCurrentYearButtonClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
