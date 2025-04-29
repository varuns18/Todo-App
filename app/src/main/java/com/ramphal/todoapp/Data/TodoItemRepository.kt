package com.ramphal.todoapp.Data

import kotlinx.coroutines.flow.Flow

class TodoItemRepository(private val todoItemDao: TodoItemDao) {

    suspend fun addTodoItem(todoItem: TodoItem){
        todoItemDao.addTodoItem(todoItem)
    }

    fun getAllTodoItem(): Flow<List<TodoItem>> = todoItemDao.getAllTodoItems()

    fun getTodoItemById(id: Long): Flow<TodoItem>{
        return todoItemDao.getTodoItemById(id = id)
    }

    suspend fun updateTodoItem(todoItem: TodoItem){
        todoItemDao.updateTodoItem(todoItemEntity = todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem){
        todoItemDao.deleteTodoItem(todoItemEntity = todoItem)
    }

}