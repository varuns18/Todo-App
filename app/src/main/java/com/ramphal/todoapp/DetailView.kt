package com.ramphal.todoapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramphal.todoapp.Data.TodoItem
import com.ramphal.todoapp.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun DetailView(
    id: Long,
    viewModel: TodoViewModel,
    navController: NavController
){

    val controller = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarData = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    if (id != 0L){
        val todoItem = viewModel.getItemById(id = id).collectAsState(initial = TodoItem(id = 0L, title = "", description = ""))
        viewModel.todoTitleState = todoItem.value.title
        viewModel.todoDescriptionState = todoItem.value.description
    }else{
        viewModel.todoTitleState = ""
        viewModel.todoDescriptionState = ""
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .imePadding()
            .windowInsetsPadding(insets = WindowInsets.ime)
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarView(
                topAppBarScrollBehavior = scrollBehavior,
                title = if (id != 0L) stringResource(R.string.update_detail) else stringResource(R.string.create_todo),
                onBackClicked = {
                    navController.navigateUp()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData   = data,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor   = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    ) {

        Column(
            modifier = Modifier.padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){

            GroupedTextField(
                firstLabel = "Title",
                firstValue = viewModel.todoTitleState,
                onFirstValueChange = { viewModel.ontodoTitleChanged(newString = it) },
                secondLabel = "Description",
                secondValue = viewModel.todoDescriptionState,
                onSecondValueChange = { viewModel.ontodoDescriptionChanged(newString = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(onClick = {

                controller?.hide()

                if (viewModel.todoTitleState.isNotEmpty() && viewModel.todoDescriptionState.isNotEmpty()){

                    if (id != 0L){
                        viewModel.updateItem(
                            TodoItem(
                                id = id,
                                title = viewModel.todoTitleState.trim(),
                                description = viewModel.todoDescriptionState.trim()
                            )
                        )
                        scope.launch {
                            navController.navigateUp()
                        }

                    }else{
                        viewModel.addItem(
                            todoItem = TodoItem(
                                title = viewModel.todoTitleState.trim(),
                                description = viewModel.todoDescriptionState.trim()
                            )
                        )
                        scope.launch {
                            navController.navigateUp()
                        }
                    }

                }else{
                    snackbarData.value = "Enter details to save"
                    scope.launch {
                        snackbarHostState.showSnackbar(message = snackbarData.value)
                    }
                }

            }) {
                Text(text = if (id != 0L) stringResource(R.string.update) else stringResource(R.string.save))
            }

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupedTextField(
    firstLabel: String,
    firstValue: String,
    onFirstValueChange: (String) -> Unit,
    secondLabel: String,
    secondValue: String,
    onSecondValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    borderColor: Color = MaterialTheme.colorScheme.outline
) {
    Column(
        modifier
            .border(BorderStroke(2.dp, borderColor), shape)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // First field with no built‑in minHeight and only 4 dp vertical padding
        TextField(
            value = firstValue,
            onValueChange = onFirstValueChange,
            label = { Text(firstLabel) },
            singleLine = true,
            modifier = Modifier.padding(2.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedContainerColor    = Color.Transparent,
                unfocusedContainerColor  = Color.Transparent,
                disabledContainerColor   = Color.Transparent,
                focusedIndicatorColor    = Color.Transparent,
                unfocusedIndicatorColor  = Color.Transparent,
                focusedLabelColor        = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor      = MaterialTheme.colorScheme.tertiary
            )
        )

        HorizontalDivider(color = borderColor, thickness = 2.dp)

        // Second field similarly constrained
        TextField(
            value = secondValue,
            onValueChange = onSecondValueChange,
            label = { Text(secondLabel) },
            modifier = Modifier.heightIn(min = 140.dp)
                .padding(2.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                focusedContainerColor    = Color.Transparent,
                unfocusedContainerColor  = Color.Transparent,
                disabledContainerColor   = Color.Transparent,
                focusedIndicatorColor    = Color.Transparent,
                unfocusedIndicatorColor  = Color.Transparent,
                focusedLabelColor        = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor      = MaterialTheme.colorScheme.tertiary
            )
        )
    }
}
