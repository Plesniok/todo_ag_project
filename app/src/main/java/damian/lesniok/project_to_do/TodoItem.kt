package damian.lesniok.project_to_do

data class TodoItem(val id: Int, val text: String, var isDone: Boolean = false)
