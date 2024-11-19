package com.example.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel (private val dbHelper: DatabaseHelper) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = dbHelper.getAllTasks()
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val task = Task(title = title, description = description)
            dbHelper.insertTask(task)
            loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            dbHelper.updateTask(task)
            loadTasks()
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            dbHelper.deleteTask(taskId)
            loadTasks()
        }
    }
}