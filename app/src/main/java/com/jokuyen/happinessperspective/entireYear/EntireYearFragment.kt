package com.jokuyen.happinessperspective.entireYear

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.R

import com.jokuyen.happinessperspective.RecyclerViewAdapter
import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.EntireYearFragmentBinding

class EntireYearFragment : Fragment() {
    private lateinit var binding: EntireYearFragmentBinding
    private lateinit var viewModel: EntireYearViewModel
    private lateinit var viewModelFactory : EntireYearViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EntireYearFragmentBinding.inflate(inflater)

        binding.recyclerView.adapter =
            RecyclerViewAdapter(
                RecyclerViewAdapter.OnClickListener {
                    // Navigate to entry's detail screen
                    this.findNavController().navigate(
                        EntireYearFragmentDirections.actionEntireYearFragmentToEntryDetailsFragment(it)
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
        viewModelFactory = EntireYearViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(EntireYearViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        activity?.title = CurrentYearSingleton.currentYear.toString()
    }

    // Overflow menu methods
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.entire_year_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entireYearMenu -> {
                viewModel.onClearCurrentYearButtonClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
