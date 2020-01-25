package com.example.happinessperspective.newEntry

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.icu.text.DateFormatSymbols
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.happinessperspective.R
import com.example.happinessperspective.database.EntryDatabase

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

        // Create an instance of the ViewModel Factory
        val application = requireNotNull(this.activity).application
        val dataSource = EntryDatabase.getInstance(application).entryDao
        val viewModelFactory = NewEntryViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewEntryViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.datePickerButton.setOnClickListener {
            showDatePickerDialog(it)
        }

        binding.submitButton.setOnClickListener {
            var subjectString: String = subject_text.text.toString()
            subjectString = subjectString.trim()

            if (TextUtils.isEmpty(subjectString)) {
                subject_text.setError("Subject cannot be empty!")
            } else {
                viewModel.setSubject(subjectString)

                var noteString: String = note_text.text.toString()
                noteString = noteString.trim()

                if (!TextUtils.isEmpty(noteString)) {
                    viewModel.setNote(noteString)
                }

                // Insert new entry into database
                viewModel.insertEntry()

                // Navigate to the next screen
            }
        }

        return binding.root
    }

    private fun showDatePickerDialog(v: View) {
        val c = Calendar.getInstance()
        val defaultYear = c.get(Calendar.YEAR)
        val defaultMonth = c.get(Calendar.MONTH)
        val defaultDay = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { _, inputYear, inputMonth, inputDay ->
                viewModel.setDate(inputYear, inputMonth, inputDay)

                val monthString = DateFormatSymbols().getMonths()[inputMonth]
                date_picker_button.text = monthString + " " + inputDay + ", " + inputYear

                changeDatePickerButtonColor()

                // Show "Submit" button
                submit_button.visibility = View.VISIBLE
            }, defaultYear, defaultMonth, defaultDay)

        datePicker.show()
    }

    private fun changeDatePickerButtonColor() {
        val newBackgroundColor = ContextCompat.getColor(requireContext(), R.color.primaryColor)
        val newTextColor = ContextCompat.getColor(requireContext(), R.color.primaryTextColor)

        // Update date picker button's color
        date_picker_button.setBackgroundTintList(ColorStateList.valueOf(newBackgroundColor))
        date_picker_button.setTextColor(newTextColor)
    }
}
