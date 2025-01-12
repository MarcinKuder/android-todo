package pl.edu.ansnt.todo.data

data class Task(
    val id: String = "",
    val title: String,
    val done: Boolean = false
) {
    val isActive: Boolean
        get() = !done
}