package com.example.prayeveryday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class NewPrayerRequestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetNewPrayerRequestScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun SetNewPrayerRequestScreen() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = { DisplayTopBar(scrollBehavior = scrollBehavior, header = "Create a New Prayer Request") },
            content = { padding -> DisplayNewPrayerRequestContent(innerPadding = padding) },
            bottomBar = { DisplayNavBar() }
        )
    }

    @Composable
    fun DisplayNewPrayerRequestContent(innerPadding: PaddingValues) {
        var requestLabel by remember { mutableStateOf("Label") }
        var requestSummary by remember { mutableStateOf("Summary") }
        var requestDetails by remember { mutableStateOf("Details") }



        Column(modifier = Modifier.padding(innerPadding)
            .fillMaxWidth()) {
            TextField(
                modifier = Modifier.padding(2.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = requestLabel,
                onValueChange = { requestLabel = it },
                label = { Text("Label") }
            )
            TextField(
                modifier = Modifier.padding(2.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = requestSummary,
                onValueChange = { requestSummary = it },
                label = { Text("Summary") }
            )
            TextField(
                    modifier = Modifier.padding(2.dp)
                        .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                value = requestDetails,
                onValueChange = { requestDetails = it },
                label = { Text("Details") },
                singleLine = false
            )
        }
    }
}
