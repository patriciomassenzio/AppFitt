package com.example.fitnessassistant

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnessapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var db :workoutDataBaseHelper
    private lateinit var  workoutAdapter : WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db  = workoutDataBaseHelper(this)
        workoutAdapter =  WorkoutAdapter(db.getAllplans(),this)

        binding.contentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.contentRecyclerView.adapter = workoutAdapter



        binding.addbtn.setOnClickListener{
           val addWorkoutIntent = Intent(this, AddWorkout::class.java)
           startActivity(addWorkoutIntent)
       }
    }

    override fun onResume() {
        super.onResume()
        workoutAdapter.refreshData(db.getAllplans())
    }


}
