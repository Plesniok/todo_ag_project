package damian.lesniok.project_to_do

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import damian.lesniok.project_to_do.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val context: Context) : androidx.lifecycle.ViewModel() {
    private var nextId = 0
    private val _todoList = mutableStateListOf<TodoItem>()
    val todoList: List<TodoItem> get() = _todoList

    private val todoRepository = TodoRepository(context)

    init {
        loadTodoList()
    }

    private fun loadTodoList() {
        viewModelScope.launch {
            todoRepository.getTodoList().collect { list ->
                _todoList.clear()
                _todoList.addAll(list)
                nextId = list.maxOfOrNull { it.id + 1 } ?: 0
            }
        }
    }

    fun addTodoItem(text: String) {
        if (text.isNotBlank()) {
            _todoList.add(TodoItem(nextId++, text))
            saveTodoList()
        }
    }

    fun toggleTodoItem(id: Int) {
        _todoList.indexOfFirst { it.id == id }.takeIf { it != -1 }?.let { index ->
            val todoItem = _todoList[index]
            _todoList[index] = todoItem.copy(isDone = !todoItem.isDone)
            saveTodoList()
        }
    }

    private fun saveTodoList() {
        viewModelScope.launch {
            todoRepository.saveTodoList(_todoList)
        }
    }
}