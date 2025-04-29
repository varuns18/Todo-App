package com.ramphal.todoapp

import android.R.attr.navigationIcon
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onBackClicked: () -> Unit = {}
){

    val navigationIcon: (@Composable () -> Unit) = {
        if (!(title.contains("Todo List"))){
            IconButton(onClick = {
                onBackClicked()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }else{
            null
        }
    }
    MediumTopAppBar(
        title = {
            Text(text = title,
                modifier = Modifier.padding(start = 4.dp),
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = navigationIcon,
        scrollBehavior = topAppBarScrollBehavior
    )
}