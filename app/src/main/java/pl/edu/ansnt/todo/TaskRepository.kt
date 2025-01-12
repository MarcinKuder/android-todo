package pl.edu.ansnt.todo

import pl.edu.ansnt.todo.data.Task
import pl.edu.ansnt.todo.data.source.local.TaskDao
import pl.edu.ansnt.todo.data.source.remote.RemoteDataSource
import pl.edu.ansnt.todo.data.toExternal
import pl.edu.ansnt.todo.data.toLocal
import pl.edu.ansnt.todo.data.toRemote

class TaskRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: TaskDao
) {
    private suspend fun refresh() {
        val remoteTasks = remoteDataSource.getAll()
        localDataSource.deleteAll()
        localDataSource.upsertAll(remoteTasks.toLocal())
    }

    suspend fun getAll(fromRemote: Boolean): List<Task> {
        if (fromRemote) {
            refresh()
        }
        return localDataSource.getAll().toExternal()
    }

    suspend fun add(task: Task) {
        remoteDataSource.add(task.toRemote())
        refresh()
    }

    suspend fun setDone(task: Task, done: Boolean) {
        val updatedTask = task.copy(done = done)
        localDataSource.update(updatedTask.toLocal())
        remoteDataSource.updateTask(updatedTask.id, updatedTask.toRemote())
    }

    suspend fun delete(task: Task) {
        localDataSource.delete(task.toLocal())
        remoteDataSource.deleteTask(task.id)
    }
}