@file:OptIn(ExperimentalMaterial3Api::class)

package pl.edu.ansnt.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.edu.ansnt.todo.model.Task
import pl.edu.ansnt.todo.ui.screens.TasksViewModel
import pl.edu.ansnt.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                ToDoApp(viewModel)
            }
        }
    }
}


@Composable
fun ToDoApp(viewModel: TasksViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { MyTopBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AddTaskPanel(viewModel, snackbarHostState)
            TasksList(viewModel, snackbarHostState)
        }
    }
}

@Composable
fun MyTopBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Box(modifier = Modifier.fillMaxWidth())
    {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    stringResource(R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            scrollBehavior = scrollBehavior,
        )
    }
}

@Composable
fun AddTaskPanel(viewModel: TasksViewModel, snackbarHostState: SnackbarHostState) {
    var title by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Enter new task") },
        )
        ElevatedButton(
            onClick = {
                viewModel.addTask(Task(title = title))
                scope.launch {
                    snackbarHostState.showSnackbar("Task added", duration = SnackbarDuration.Short)
                }
                title = ""
            }, modifier = Modifier
                .height(56.dp)
                .padding(8.dp, 16.dp, 0.dp, 0.dp),
            enabled = title.isNotBlank()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Todo")
        }
    }
}

@Composable
fun TasksList(viewModel: TasksViewModel, snackbarHostState: SnackbarHostState) {
    val tasks by viewModel.tasks.collectAsState()
    val error by viewModel.error.collectAsState()
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        if (error != null) {
            item {
                Text(error.toString(), modifier = Modifier.padding(8.dp))
            }
        }
        items(tasks) { task ->
            Card(
                modifier = Modifier
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val style = if (task.done) {
                        TextStyle(
                            textDecoration = TextDecoration.LineThrough,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    } else {
                        TextStyle()
                    }
                    Text(text = task.title, style = style)
                    Row() {
                        IconButton(onClick = {
                            viewModel.deleteTask(task)
                            scope.launch {
                                snackbarHostState.showSnackbar("Task deleted", duration = SnackbarDuration.Short)
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                        OutlinedIconButton(onClick = {
                            viewModel.setDone(task)
                        }, enabled = !task.done) {

                            Icon(Outlined.Done, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}

