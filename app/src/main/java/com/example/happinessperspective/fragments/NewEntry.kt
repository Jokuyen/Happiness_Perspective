package com.example.happinessperspective.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.happinessperspective.R
import com.example.happinessperspective.databinding.NewEntryFragmentBinding
import com.example.happinessperspective.viewModels.NewEntryViewModel

class NewEntry : Fragment() {

    private lateinit var viewModel: NewEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NewEntryFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewEntryViewModel::class.java)
    }

}
