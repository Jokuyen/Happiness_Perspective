package com.jokuyen.happinessperspective.currentMonth

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jokuyen.happinessperspective.R
import com.jokuyen.happinessperspective.database.EntryDao

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

        setHasOptionsMenu(true)

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
