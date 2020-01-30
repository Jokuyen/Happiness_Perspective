package com.example.happinessperspective.entryDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.happinessperspective.R
import com.example.happinessperspective.databinding.EntryDetailsFragmentBinding

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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val entry = EntryDetailsArgs.fromBundle(arguments!!).selectedEntry
        val application = requireNotNull(activity).application

        viewModelFactory = EntryDetailsViewModelFactory(entry, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EntryDetailsViewModel::class.java)

        binding.viewModel = viewModel
    }

}
