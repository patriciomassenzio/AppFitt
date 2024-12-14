package com.example.fitnessassistant

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fitnessapp.databinding.ActivityAddWorkoutBinding



class AddWorkout : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkoutBinding
    private  lateinit var db: workoutDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = workoutDataBaseHelper(this)

        binding.workoutsavebtn.setOnClickListener{
            val date = binding.wdate.text.toString()
            val content = binding.workoutcontent.text.toString()
            val Workout = workout(0,date, content)
            db.insertworkout(Workout)
            finish()
            Toast.makeText(this,"Workout Saved",Toast.LENGTH_SHORT).show()
        }
    }
}