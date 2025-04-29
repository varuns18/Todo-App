package com.ramphal.todoapp

import android.content.Context
import androidx.room.Room
import com.ramphal.todoapp.Data.TodoItemDataBase
import com.ramphal.todoapp.Data.TodoItemRepository

object Graph {
    lateinit var dataBase: TodoItemDataBase

    val todoItemRepository by lazy {
        TodoItemRepository(todoItemDao = dataBase.TodoItemDao())
    }


    fun provide(context: Context){
        dataBase = Room.databaseBuilder(
            context = context,
            klass = TodoItemDataBase::class.java,
            name = "Todolist.db"
        ).build()
    }

}