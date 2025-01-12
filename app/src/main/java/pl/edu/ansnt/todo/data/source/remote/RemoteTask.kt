package pl.edu.ansnt.todo.data.source.remote

data class RemoteTask(
    val id: String = "",
    val title: String,
    val done: Boolean = false
)