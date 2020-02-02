package com.jokuyen.happinessperspective

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

// Credit:
// https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04

@RunWith(AndroidJUnit4::class)
class LiveDataTest : HelperFunction {

    class MyViewModel : ViewModel() {
        private val _liveData1 = MutableLiveData<String>()
        val liveData1: LiveData<String> = _liveData1

        // [Transformations.map] on liveData1 that converts the value to uppercase:
        val liveData2 = Transformations.map(_liveData1) {it.toUpperCase()}

        fun setNewValue(newValue: String) {
            GlobalScope.launch(Dispatchers.Main) {
                _liveData1.value = newValue
            }
        }
    }

    @Test
    fun isLiveDataEmitting_getOrAwaitValue() {
        val viewModel = MyViewModel()

        viewModel.setNewValue("tEsT")

        assertEquals(viewModel.liveData1.getOrAwaitValue(), "tEsT")
        assertEquals(viewModel.liveData2.getOrAwaitValue(), "TEST")
    }
}