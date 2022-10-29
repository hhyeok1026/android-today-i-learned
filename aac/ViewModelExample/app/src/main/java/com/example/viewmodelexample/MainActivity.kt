package com.example.viewmodelexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*
        var counter = 100
        binding.textView.text = counter.toString()

        binding.button.setOnClickListener {
            counter += 1
            binding.textView.text = counter.toString()
        }*/


        //val myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        //myViewModel.counter = 100
        /*binding.textView.text = myViewModel.counter.toString()

        binding.button.setOnClickListener {
            myViewModel.counter += 1
            binding.textView.text = myViewModel.counter.toString()
        }*/

        val factory = MyViewModelFactory(100, this)
        // val myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
        val myViewModel by viewModels<MyViewModel>() { factory }

        binding.textView.text = myViewModel.counter.toString()
        binding.button.setOnClickListener {
            myViewModel.counter += 1
            myViewModel.saveState()
            binding.textView.text = myViewModel.counter.toString()
        }

    }
}




















