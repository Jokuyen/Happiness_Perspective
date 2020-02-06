package com.jokuyen.happinessperspective.entireYear

import android.icu.text.DateFormatSymbols
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.R

import com.jokuyen.happinessperspective.RecyclerViewAdapter
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDatabase
import com.jokuyen.happinessperspective.databinding.EntireYearFragmentBinding
import kotlinx.android.synthetic.main.entire_year_fragment.*

//Todo: Try changing RecyclerViewListener to reduce flickering
//Todo: Get rid of Entry's date property and change parameters of Dao's methods from using Strings to Ints when accessing dates

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

        // Setup ChipGroup
        val chipGroup = binding.monthChipGroup
        val inflater = LayoutInflater.from(chipGroup.context)

        // 2. Make new Chip view for each month
        for (i in 0..11) {
            val chip = inflater.inflate(R.layout.month_chip, chipGroup, false) as Chip
            chip.text = DateFormatSymbols().getMonths()[i]
            chip.tag = i

            chip.setOnCheckedChangeListener { button, isChecked ->
                viewModel.onFilterChanged(button.tag as Int, isChecked)
            }

            chipGroup.addView(chip)
        }

        binding.recyclerView.itemAnimator?.setChangeDuration(0)
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
