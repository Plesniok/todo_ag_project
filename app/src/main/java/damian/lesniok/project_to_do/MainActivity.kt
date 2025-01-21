package damian.lesniok.project_to_do

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    TodoApp(TodoViewModel(context = applicationContext))
                }
            }
        }
    }
}

@Composable
fun TodoApp(viewModel: TodoViewModel = viewModel()) {
    val todoList = viewModel.todoList
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AddTodoFab { text ->
                viewModel.addTodoItem(text)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 50.dp, horizontal = 10.dp)
        ) {
            Text("To-Do List", style = MaterialTheme.typography.h5, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            TodoList(todoList = todoList, onToggle = { id -> viewModel.toggleTodoItem(id) })
        }
    }
}

@Composable
fun AddTodoFab(onAddTodo: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddTodoDialog(
            onAdd = {
                onAddTodo(it)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    FloatingActionButton(onClick = { showDialog = true }) {
        Text("+")
    }
}

@Composable
fun AddTodoDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add To-Do") },
        text = {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                textStyle = TextStyle(fontSize = 18.sp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onAdd(text) })
            )
        },
        confirmButton = {
            TextButton(onClick = { onAdd(text) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TodoList(todoList: List<TodoItem>, onToggle: (Int) -> Unit) {
    Column {
        todoList.forEach { todo ->
            TodoItemView(todo = todo, onToggle = { onToggle(todo.id) })
        }
    }
}

@Composable
fun TodoItemView(todo: TodoItem, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (todo.isDone) Color.Green.copy(alpha = 0.2f) else Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = todo.isDone, onCheckedChange = { onToggle() })
        Text(
            text = todo.text,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
