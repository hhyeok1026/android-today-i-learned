package com.example.viewmodelexample

import androidx.lifecycle.*

/*class MyViewModel : ViewModel() {
    var counter : Int = 0
}*/

class MyViewModel(
    _counter : Int,
    private val repositoryImpl: MyRepositoryImpl,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val counterFromRepository : LiveData<Int> = repositoryImpl.getCounter()

    fun increaseCounter() {
        repositoryImpl.increaseCounter()
    }

    companion object {
        private const val SAVE_STATE_KEY = "counter"
    }

    // var counter = _counter
    var liveCounter : MutableLiveData<Int> = MutableLiveData(_counter)
    val modifierCounter : LiveData<String> = Transformations.map(liveCounter) { counter ->
        "$counter 입니다."
    }

    val hasChecked : MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    var counter = savedStateHandle.get<Int>(SAVE_STATE_KEY) ?: _counter

    fun saveState() {
        savedStateHandle.set(SAVE_STATE_KEY, counter)
    }
}
