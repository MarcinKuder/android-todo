package pl.edu.ansnt.todo.model

data class Task(
    val id: String = "",
    val title: String,
    var done: Boolean = false
)