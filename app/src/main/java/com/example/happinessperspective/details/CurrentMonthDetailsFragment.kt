package com.example.happinessperspective.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.happinessperspective.R
import com.example.happinessperspective.databinding.CurrentMonthDetailsFragmentBinding

class CurrentMonthDetailsFragment : Fragment() {

    private lateinit var viewModel: CurrentMonthDetailsViewModel
    private lateinit var binding: CurrentMonthDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentMonthDetailsFragmentBinding.inflate(inflater)

        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            // Navigate to entry's detail screen
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentMonthDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
