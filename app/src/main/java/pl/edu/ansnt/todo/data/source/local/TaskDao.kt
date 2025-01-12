package pl.edu.ansnt.todo.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    suspend fun getAll(): List<LocalTask>

//    @Query("SELECT * FROM task WHERE NOT done")
//    fun getNotDone(): List<Task>

    @Insert
    suspend fun insert(task: LocalTask)

    @Update
    suspend fun update(task: LocalTask)

    @Delete
    suspend fun delete(user: LocalTask)

    @Query("DELETE FROM task")
    suspend fun deleteAll()

    @Upsert
    suspend fun upsertAll(tasks: List<LocalTask>)
}