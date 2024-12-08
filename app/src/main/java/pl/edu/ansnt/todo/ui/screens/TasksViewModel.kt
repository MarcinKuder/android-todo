package pl.edu.ansnt.todo.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.edu.ansnt.todo.model.Task
import pl.edu.ansnt.todo.network.TasksAPI

class TasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                val fetchedTasks = TasksAPI.service.getTasks()
                _tasks.value = fetchedTasks
            } catch (e: Exception) {
                _error.value = "An error occurred while fetching tasks"
                Log.e("TasksViewModel", "Error fetching tasks: " + e.message)
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            TasksAPI.service.addTask(task)
            fetchTasks() // Refresh the list after adding a new task
        }
    }

    fun setDone(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(done = !task.done)
            TasksAPI.service.updateTask(task.id, updatedTask)
            fetchTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            TasksAPI.service.deleteTask(task.id)
            fetchTasks()
        }
    }
}
