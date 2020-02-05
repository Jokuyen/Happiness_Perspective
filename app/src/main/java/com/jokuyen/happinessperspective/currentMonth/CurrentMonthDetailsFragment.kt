package com.jokuyen.happinessperspective.currentMonth

import android.icu.util.Calendar
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.R

import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.CurrentMonthDetailsFragmentBinding

class CurrentMonthDetailsFragment : Fragment() {
    private lateinit var binding: CurrentMonthDetailsFragmentBinding
    private lateinit var viewModel: CurrentMonthDetailsViewModel
    private lateinit var viewModelFactory: CurrentMonthDetailsViewModelFactory

    // Default values when navigating from Navigation Drawer
    private val c = Calendar.getInstance()
    private var currentYear = CurrentYearSingleton.currentYear.toString()
    private var currentMonth = String.format("%02d", c.get(Calendar.MONTH) + 1)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentMonthDetailsFragmentBinding.inflate(inflater)

        // Override default values when navigating from NewEntry fragment
        if (arguments!!.size() > 0) {
            val args = CurrentMonthDetailsFragmentArgs.fromBundle(arguments!!)
            currentYear = args.yearArg.toString()
            currentMonth = String.format("%02d", args.monthArg + 1)
        }

        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            // Navigate to entry's detail screen
            this.findNavController().navigate(
                CurrentMonthDetailsFragmentDirections.actionCurrentMonthDetailsFragmentToEntryDetails(it))
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        viewModelFactory = CurrentMonthDetailsViewModelFactory(currentYear, currentMonth, dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentMonthDetailsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

    }

    // Overflow menu methods
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.current_month_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.currentMonthMenu -> {
                viewModel.onClearCurrentMonthAndYearButtonClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
