package com.example.fitnessassistant

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R

class WorkoutAdapter(private var workoutList: List<workout>,context: Context) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val db : workoutDataBaseHelper = workoutDataBaseHelper(context)

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateview)
        val contentTextView: TextView = itemView.findViewById(R.id.contentview)
        val updatebutton: ImageView = itemView.findViewById(R.id.updatebtn)
        val deletebutton: ImageView = itemView.findViewById(R.id.deletebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_items, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun getItemCount(): Int = workoutList.size

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workoutList[position]
        holder.dateTextView.text = workout.date
        holder.contentTextView.text = workout.content

        holder.updatebutton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply{
                putExtra("workout_id",workout.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deletebutton.setOnClickListener{
            db.deleteworkout(workout.id)
            refreshData(db.getAllplans())
            Toast.makeText(holder.itemView.context,"Workout Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newWorkout : List<workout>){
        workoutList = newWorkout
        notifyDataSetChanged()
    }

}
