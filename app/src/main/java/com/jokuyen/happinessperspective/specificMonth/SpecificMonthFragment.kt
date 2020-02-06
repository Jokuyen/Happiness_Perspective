package com.jokuyen.happinessperspective.specificMonth

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.jokuyen.happinessperspective.R
import com.jokuyen.happinessperspective.RecyclerViewAdapter
import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.SpecificMonthFragmentBinding

class SpecificMonthFragment : Fragment() {
    private lateinit var binding: SpecificMonthFragmentBinding
    private lateinit var viewModel: SpecificMonthViewModel
    private lateinit var viewModelFactory: SpecificMonthViewModelFactory

    private var yearArg: Int = -1
    private var monthArg: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SpecificMonthFragmentBinding.inflate(inflater)

        // Grab values from SafeArgs
        val args = SpecificMonthFragmentArgs.fromBundle(arguments!!)
        yearArg = args.yearArg
        monthArg = args.monthArg + 1

        binding.recyclerView.adapter =
            RecyclerViewAdapter(
                RecyclerViewAdapter.OnClickListener {
                    // Navigate to entry's detail screen
                    this.findNavController().navigate(
                        SpecificMonthFragmentDirections.actionSpecificMonthFragmentToEntryDetailsFragment(it)
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
        viewModelFactory = SpecificMonthViewModelFactory(yearArg, monthArg, dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(SpecificMonthViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        activity?.title = viewModel.getDateString()
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
