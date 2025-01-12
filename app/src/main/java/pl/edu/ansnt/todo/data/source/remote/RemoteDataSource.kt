package pl.edu.ansnt.todo.data.source.remote

class RemoteDataSource(
    private val tasksAPI: TasksAPI
) {
    suspend fun getAll(): List<RemoteTask> {
        return try {
            tasksAPI.service.getTasks()
        } catch (e: Exception) {
            // todo: handle exception
            emptyList()
        }
    }

    suspend fun get(id: String): RemoteTask? {
        return try {
            tasksAPI.service.getTask(id)
        } catch (e: Exception) {
            return null
            // todo: handle exception
        }
    }

    suspend fun add(task: RemoteTask) {
        try {
            tasksAPI.service.addTask(task)
        } catch (e: Exception) {
            // todo: handle exception
        }
    }

    suspend fun updateTask(id: String, task: RemoteTask) {
        try {
            tasksAPI.service.updateTask(id, task)
        } catch (e: Exception) {
            // todo: handle exception
        }
    }

    suspend fun deleteTask(id: String) {
        try {
            tasksAPI.service.deleteTask(id)
        } catch (e: Exception) {
            // todo: handle exception
        }
    }

}