package pl.edu.ansnt.todo.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.edu.ansnt.todo.TaskRepository
import pl.edu.ansnt.todo.TodoApplication
import pl.edu.ansnt.todo.data.Task
import pl.edu.ansnt.todo.data.source.remote.RemoteDataSource
import pl.edu.ansnt.todo.data.source.remote.TasksAPI

private const val TAG = "TasksViewModel"

class TasksViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val taskRepository: TaskRepository = TaskRepository(
        RemoteDataSource(TasksAPI),
        TodoApplication.database.taskDao()
    )

    init {
        refresh()
    }

    private fun refresh(fromRemote: Boolean = true) {
        viewModelScope.launch {
            try {
                val fetchedTasks = taskRepository.getAll(fromRemote)
                _tasks.value = fetchedTasks
            } catch (e: Exception) {
                _error.value = "An error occurred while fetching tasks"
                Log.w(TAG, "fetchTasks: ${e.message}")
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.add(task)
                refresh(fromRemote = false)
            } catch (e: Exception) {
                _error.value = "An error occurred while adding a task"
                Log.e(TAG, "addTask: ${e.message}")
            }
        }
    }

    fun setDone(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.setDone(task, true)
                refresh(fromRemote = false)
            } catch (e: Exception) {
                _error.value = "An error occurred while updating a task"
                Log.e(TAG, "setDone: ${e.message}")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.delete(task)
                refresh(fromRemote = false)
            } catch (e: Exception) {
                _error.value = "An error occurred while deleting a task"
                Log.e(TAG, "deleteTask: ${e.message}")
            }
        }
    }
}
