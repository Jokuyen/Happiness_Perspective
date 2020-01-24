package com.example.happinessperspective.newEntry

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.icu.text.DateFormatSymbols
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.happinessperspective.R

import com.example.happinessperspective.databinding.NewEntryFragmentBinding
import kotlinx.android.synthetic.main.new_entry_fragment.*

class NewEntryFragment : Fragment() {

    private lateinit var binding: NewEntryFragmentBinding
    private lateinit var viewModel: NewEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NewEntryFragmentBinding.inflate(inflater)

        binding.datePickerButton.setOnClickListener {
            showDatePickerDialog(it)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewEntryViewModel::class.java)
    }

    private fun showDatePickerDialog(v: View) {
        val c = Calendar.getInstance()
        val defaultYear = c.get(Calendar.YEAR)
        val defaultMonth = c.get(Calendar.MONTH)
        val defaultDay = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { _, inputYear, inputMonth, inputDay ->
                val monthString = DateFormatSymbols().getMonths()[inputMonth]
                date_picker_button.text = monthString + " " + inputDay + ", " + inputYear

                val newColor = ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
                date_picker_button.setBackgroundTintList(ColorStateList.valueOf(newColor))
            }, defaultYear, defaultMonth, defaultDay)

        datePicker.show()
    }
}
