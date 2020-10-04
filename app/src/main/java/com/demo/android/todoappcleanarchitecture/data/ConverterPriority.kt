package com.demo.android.todoappcleanarchitecture.data

import androidx.room.TypeConverter
import com.demo.android.todoappcleanarchitecture.data.models.Priority

class ConverterPriority {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority{
        return Priority.valueOf(priority)
    }

}