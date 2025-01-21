package damian.lesniok.project_to_do

import androidx.compose.runtime.*

class TodoViewModel : androidx.lifecycle.ViewModel() {
    private var nextId = 0
    private val _todoList = mutableStateListOf<TodoItem>()
    val todoList: List<TodoItem> get() = _todoList

    fun addTodoItem(text: String) {
        if (text.isNotBlank()) {
            _todoList.add(TodoItem(nextId++, text))
        }
    }

    fun toggleTodoItem(id: Int) {
        _todoList.find { it.id == id }?.let {
            it.isDone = !it.isDone
        }
    }
}