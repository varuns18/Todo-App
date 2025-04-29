package com.ramphal.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramphal.todoapp.Data.TodoItem
import com.ramphal.todoapp.Data.TodoItemRepository
import com.ramphal.todoapp.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoItemRepository: TodoItemRepository = Graph.todoItemRepository
): ViewModel() {


    var todoTitleState by mutableStateOf("")
    var todoDescriptionState by mutableStateOf("")


    fun ontodoTitleChanged(newString: String){
        todoTitleState = newString
    }

    fun ontodoDescriptionChanged(newString: String){
        todoDescriptionState = newString
    }

    lateinit var getAllTodoItem: Flow<List<TodoItem>>

    init {
        viewModelScope.launch {
            getAllTodoItem = todoItemRepository.getAllTodoItem()
        }
    }

    fun addItem(todoItem: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            todoItemRepository.addTodoItem(todoItem = todoItem)
        }
    }

    fun getItemById(id: Long): Flow<TodoItem> {
        return todoItemRepository.getTodoItemById(id = id)
    }

    fun updateItem(todoItem: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            todoItemRepository.updateTodoItem(todoItem = todoItem)
        }
    }

    fun deleteItem(todoItem: TodoItem){
        viewModelScope.launch(Dispatchers.IO) {
            todoItemRepository.deleteTodoItem(todoItem = todoItem)
        }
    }

}

