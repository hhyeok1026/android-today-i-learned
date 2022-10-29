package com.example.viewmodelexample

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/*class MyViewModel : ViewModel() {
    var counter : Int = 0
}*/

class MyViewModel(
    _counter : Int,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val SAVE_STATE_KEY = "counter"
    }

    // var counter = _counter
    var counter = savedStateHandle.get<Int>(SAVE_STATE_KEY) ?: _counter

    fun saveState() {
        savedStateHandle.set(SAVE_STATE_KEY, counter)
    }
}
