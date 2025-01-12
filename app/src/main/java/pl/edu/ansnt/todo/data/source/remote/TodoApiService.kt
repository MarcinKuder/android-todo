package pl.edu.ansnt.todo.data.source.remote

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
    suspend fun getTasks(): List<RemoteTask>

    @GET("/tasks/{id}")
    suspend fun getTask(@Path("id") id: String): RemoteTask

    @POST("/tasks")
    suspend fun addTask(@Body task: RemoteTask) : RemoteTask

    @PUT("/tasks/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: RemoteTask) : RemoteTask

    @DELETE("/tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String)
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