package pl.edu.ansnt.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.edu.ansnt.todo.data.source.local.LocalTask
import pl.edu.ansnt.todo.data.source.local.TaskDao

@Database(entities = [LocalTask::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}