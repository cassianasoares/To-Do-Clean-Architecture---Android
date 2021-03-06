package com.demo.android.todoappcleanarchitecture.data.repository

import androidx.lifecycle.LiveData
import com.demo.android.todoappcleanarchitecture.data.ToDoDao
import com.demo.android.todoappcleanarchitecture.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPrioriTy()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPrioriTy()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return toDoDao.searchDatabase(searchQuery)
    }



}