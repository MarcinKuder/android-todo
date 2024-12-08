package pl.edu.ansnt.todo.network

import pl.edu.ansnt.todo.model.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TasksApiService {
    @GET("/tasks")
    suspend fun getTasks(): List<Task>

    @POST("/tasks")
    suspend fun addTask(@Body task: Task): Task

    @PUT("/tasks/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: Task): Task

    @DELETE("/tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String)
}

object TasksAPI {
    private const val BASE_URL = ""

    val service: TasksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TasksApiService::class.java)
    }
}