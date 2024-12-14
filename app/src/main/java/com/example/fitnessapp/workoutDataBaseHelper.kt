package com.example.fitnessassistant

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class workoutDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "planapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "plans"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "date"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT , $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertworkout(Workout:workout){
        val db = writableDatabase
        val value = ContentValues().apply{
            put(COLUMN_TITLE,Workout.date)
            put(COLUMN_CONTENT,Workout.content)
        }
        db.insert(TABLE_NAME,null,value)
        db.close()
    }

    fun getAllplans():List<workout>{
        val workoutlist = mutableListOf<workout>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val Workout = workout(id,date,content)
            workoutlist.add(Workout)
        }
        cursor.close()
        db.close()
        return workoutlist
    }

    fun updateworkout(Workout: workout){
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE,Workout.date)
            put(COLUMN_CONTENT,Workout.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(Workout.id.toString())
        db.update(TABLE_NAME,value,whereClause,whereArgs)
        db.close()
    }

    fun getworkoutByID(workoutid:Int):workout{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $workoutid"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return workout(id,date,content)
    }

    fun deleteworkout(workoutid: Int){
        val db=writableDatabase
        val whereClause = "$COLUMN_ID=?"
        val whereArgs = arrayOf(workoutid.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}
