package pl.edu.ansnt.todo.network

import pl.edu.ansnt.todo.model.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TasksApiService {
    @GET("/tasks")
    suspend fun getTasks(): List<Task>
}

object TasksAPI {
    private const val BASE_URL = "https://todoapi-axn2q3z8dtr9.deno.dev"

    val service: TasksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TasksApiService::class.java)
    }
}