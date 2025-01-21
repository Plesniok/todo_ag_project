package damian.lesniok.project_to_do.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import damian.lesniok.project_to_do.TodoItem
import damian.lesniok.project_to_do.models.TodoItemSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.todoDataStore: DataStore<Preferences> by preferencesDataStore(name = "todo_data_store")

class TodoRepository(context: Context) {

    private val dataStore = context.todoDataStore

    private val todoListKey = stringPreferencesKey("todo_list_key")

    suspend fun saveTodoList(todoList: List<TodoItem>) {
        val todoListJson = TodoItemSerializer.toJson(todoList)
        dataStore.edit { preferences ->
            preferences[todoListKey] = todoListJson
        }
    }

    fun getTodoList(): Flow<List<TodoItem>> {
        return dataStore.data
            .map { preferences ->
                preferences[todoListKey]?.let {
                    TodoItemSerializer.fromJson(it)
                } ?: emptyList()
            }
    }
}
