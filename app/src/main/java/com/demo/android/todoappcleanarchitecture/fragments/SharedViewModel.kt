package com.demo.android.todoappcleanarchitecture.fragments

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demo.android.todoappcleanarchitecture.R
import com.demo.android.todoappcleanarchitecture.data.models.Priority
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData
import com.demo.android.todoappcleanarchitecture.fragments.settings.Constants
import com.demo.android.todoappcleanarchitecture.fragments.settings.Mode

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoData: List<ToDoData>){
        emptyDatabase.value = toDoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

    }

    fun verifyDataFromUser(title: String, description: String): Boolean{
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> Priority.LOW
        }
    }

}