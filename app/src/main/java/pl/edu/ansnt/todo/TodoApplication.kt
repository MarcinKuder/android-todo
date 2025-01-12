package pl.edu.ansnt.todo

import android.app.Application
import androidx.room.Room
import pl.edu.ansnt.todo.data.AppDatabase

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "todo-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var database: AppDatabase
            private set
    }
}