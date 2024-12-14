package com.example.fitnessassistant

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: workoutDataBaseHelper
    private var workoutid: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = workoutDataBaseHelper(this)

        workoutid = intent.getIntExtra("workout_id", -1)
        if (workoutid == -1) {
            finish()
            return
        }

        val workout = db.getworkoutByID(workoutid)
        binding.updatedate.setText(workout.date)
        binding.updateworkoutcontent.setText(workout.content)

        binding.updateSavebtn.setOnClickListener {
            val newdate = binding.updatedate.text.toString()
            val newcontent = binding.updateworkoutcontent.text.toString()
            val updateworkout = workout(workoutid, newdate, newcontent)
            db.updateworkout(updateworkout)
            finish()
            Toast.makeText(this, "Workout Updated", Toast.LENGTH_SHORT).show()
        }
    }
}
