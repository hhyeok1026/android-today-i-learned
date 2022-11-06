package com.example.viewmodelexample

import androidx.lifecycle.LiveData

interface MyRepository {
    fun getCounter(): LiveData<Int>
    fun increaseCounter()
}