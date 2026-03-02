package com.example.favoritetwittersearches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.favoritetwittersearches.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TwitterSearchApp()
                }
            }
        }
    }
}

@Composable
fun TwitterSearchApp() {
    var queryText by remember { mutableStateOf("") }
    var tagText by remember { mutableStateOf("") }

    // Користиме mutableStateListOf за автоматско ажурирање на UI
    val searchList = remember { mutableStateListOf<TaggedSearch>() }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = queryText,
            onValueChange = { queryText = it },
            label = { Text("Enter Twitter search query here") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tagText,
                onValueChange = { tagText = it },
                label = { Text("Tag your query") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    if (queryText.isNotBlank() && tagText.isNotBlank()) {
                        searchList.add(TaggedSearch(tagText, queryText))
                        queryText = ""
                        tagText = ""
                    }
                }
            ) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tagged Searches", style = MaterialTheme.typography.headlineSmall)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(searchList) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.tag, modifier = Modifier.weight(1f))
                        Text(
                            text = "Edit",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { searchList.clear() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Clear Tags")
        }
    }
}