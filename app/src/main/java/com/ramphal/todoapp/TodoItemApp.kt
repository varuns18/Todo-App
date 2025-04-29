package com.ramphal.todoapp

import android.app.Application

class TodoItemApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(context = this)
    }

}