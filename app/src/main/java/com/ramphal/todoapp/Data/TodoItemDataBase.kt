package com.ramphal.todoapp.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoItem::class],
    version = 1,
    exportSchema = false
)
abstract class TodoItemDataBase: RoomDatabase(){

    abstract fun TodoItemDao(): TodoItemDao

}