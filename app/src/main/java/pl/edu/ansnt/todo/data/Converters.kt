package pl.edu.ansnt.todo.data

import pl.edu.ansnt.todo.data.source.local.LocalTask
import pl.edu.ansnt.todo.data.source.remote.RemoteTask

fun LocalTask.toExternal() = Task(
    id = id,
    title = title,
    done = done
)

@JvmName("localToExternal")
fun List<LocalTask>.toExternal() = map(LocalTask::toExternal)

fun RemoteTask.toExternal() = Task(
    id = id,
    title = title,
    done = done
)

@JvmName("remoteToExternal")
fun List<RemoteTask>.toExternal() = map(RemoteTask::toExternal)

fun RemoteTask.toLocal() = LocalTask(
    id = id,
    title = title,
    done = done,
)

@JvmName("remoteToLocal")
fun List<RemoteTask>.toLocal() = map(RemoteTask::toLocal)

fun Task.toLocal() = LocalTask(
    id = id,
    title = title,
    done = done
)

@JvmName("externalToLocal")
fun List<Task>.toLocal() = map(Task::toLocal)

fun Task.toRemote() = RemoteTask(
    id = id,
    title = title,
    done = done
)

@JvmName("externalToRemote")
fun List<Task>.toRemote() = map(Task::toRemote)