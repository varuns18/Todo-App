package com.ramphal.todoapp.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoItemDao {

    @Insert(onConflict = IGNORE)
    abstract suspend fun addTodoItem(todoItemEntity: TodoItem)

    @Delete
    abstract suspend fun deleteTodoItem(todoItemEntity: TodoItem)

    @Query(value = "Select * from `todoitem-table`")
    abstract fun getAllTodoItems(): Flow<List<TodoItem>>

    @Query(value = "Select * from `todoitem-table` where id=:id")
    abstract fun getTodoItemById(id: Long): Flow<TodoItem>

    @Update(onConflict = IGNORE)
    abstract suspend fun updateTodoItem(todoItemEntity: TodoItem)

}