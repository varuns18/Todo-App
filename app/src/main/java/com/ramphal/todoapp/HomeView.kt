package com.ramphal.todoapp

import android.R.attr.name
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramphal.todoapp.Data.TodoItem
import com.ramphal.todoapp.Data.demoData
import com.ramphal.todoapp.navigation.Screen
import com.ramphal.todoapp.ui.theme.TodoAppTheme
import com.ramphal.todoapp.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: TodoViewModel
){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyColumnState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarView(title = "Todo List", topAppBarScrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                contentColor = MaterialTheme.colorScheme.primaryContainer,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(route = Screen.detailView.route + "/0L")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { innerpadding->
        val todoList = viewModel.getAllTodoItem.collectAsState(initial = listOf())
        LazyColumn(state = lazyColumnState,
            modifier = Modifier
            .padding(innerpadding)
            .fillMaxSize()) {
            items(items = todoList.value, key = { it.id }) {
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { newValue ->
                        if (newValue == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteItem(it)
                        }
                        true
                    },
                    positionalThreshold = { totalDistance -> totalDistance * 0.55f }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
                                Color.Red
                            }else{
                                Color.Transparent
                            },
                            label = ""
                        )
                        val alignment = Alignment.CenterEnd

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(end = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    },
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = false
                ) {
                    TodoItem(todoItem = it) {
                        val id = it.id
                        navController.navigate(Screen.detailView.route + "/$id")
                    }
                }
            }
        }
    }

}

@Composable
fun TodoItem(todoItem: TodoItem, onClick: () -> Unit){

    Card (
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = todoItem.title, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text(text = todoItem.description,
                color = MaterialTheme.colorScheme.tertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    TodoAppTheme {
        HomeView(navController = rememberNavController(), viewModel = TodoViewModel())
    }
}