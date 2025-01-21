package damian.lesniok.project_to_do.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import damian.lesniok.project_to_do.TodoItem

object TodoItemSerializer {
    private val gson = Gson()
    fun toJson(todoList: List<TodoItem>): String {
        return gson.toJson(todoList)
    }
    fun fromJson(json: String): List<TodoItem> {
        val type = object : TypeToken<List<TodoItem>>() {}.type
        return gson.fromJson(json, type)
    }
}
