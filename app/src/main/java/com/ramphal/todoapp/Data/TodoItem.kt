package com.ramphal.todoapp.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoitem-table")
data class TodoItem (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "item-title")
    val title: String,
    @ColumnInfo(name = "item-desc")
    val description: String
)

object demoData{
    val TodoList = listOf<TodoItem>(
        TodoItem(id = 1, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 2, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 3, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 4, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 5, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 6, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 7, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 8, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 9, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 10, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 11, title = "Hello", description = "i am varun How are you"),
        TodoItem(id = 12, title = "Hello", description = "i am varun How are you")
    )
}