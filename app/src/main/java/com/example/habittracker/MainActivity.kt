package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.HabitTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HabitTrackerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HabitTrackerScreen(modifier: Modifier = Modifier) {
    //Saves state of text field through recomposition
    var habitText by rememberSaveable { mutableStateOf("") }
    //keeps track of input and time stamp list
    var habits by rememberSaveable { mutableStateOf(listOf<Pair<String, String>>()) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        //habit list label
        Text(
            text = "Habit List",
            style = MaterialTheme.typography.headlineLarge
        )
        //adds space between input and label
        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Habit: ")

        TextField(
            value = habitText,
            onValueChange = { habitText = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        //space for button
        Spacer(modifier = Modifier.height(10.dp))

        //Add button
        Button(
            onClick = {
                if (habitText.isNotBlank()) {
                    val timestamp = SimpleDateFormat(
                        "MMM dd, HH:mm",
                        Locale.getDefault()
                    ).format(
                        Date()
                    )
                    habits = (habits + (habitText to timestamp))
                    habitText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(20.dp))

        //display habit list
        LazyColumn {
            items(habits) { habit ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Text(
                        text = "${habit.first} - ${habit.second}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitTrackerPreview() {
    HabitTrackerTheme {
        HabitTrackerScreen()
    }
}