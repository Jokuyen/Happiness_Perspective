package com.jokuyen.happinessperspective.entryDetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.jokuyen.happinessperspective.R
import com.jokuyen.happinessperspective.database.EntryDatabase

import com.jokuyen.happinessperspective.databinding.EntryDetailsFragmentBinding

class EntryDetailsFragment : Fragment() {

    private lateinit var binding: EntryDetailsFragmentBinding
    private lateinit var viewModel: EntryDetailsViewModel
    private lateinit var viewModelFactory: EntryDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EntryDetailsFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val entry = EntryDetailsFragmentArgs.fromBundle(arguments!!).selectedEntry
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao

        // Create an instance of the ViewModel Factory
        viewModelFactory = EntryDetailsViewModelFactory(entry, dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EntryDetailsViewModel::class.java)

        binding.viewModel = viewModel
    }

    // Overflow menu methods
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.entry_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_current_entry -> {
                viewModel.onDeleteCurrentEntryButtonClick()
                parentFragmentManager.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
