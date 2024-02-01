package com.example.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.datastore.datastore.StoreUserEmail
import com.example.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserEmail(context)

    val savedUsernameState by dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState by dataStore.getEmail.collectAsState(initial = "")
    val savedIdState by dataStore.getId.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(16.dp, top = 30.dp),
            text = "Username",
            color = Color.Gray,
            fontSize = 12.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
        )

        Text(
            modifier = Modifier
                .padding(16.dp, top = 10.dp),
            text = "Email",
            color = Color.Gray,
            fontSize = 12.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
        )

        Text(
            modifier = Modifier
                .padding(16.dp, top = 10.dp),
            text = "College ID",
            color = Color.Gray,
            fontSize = 12.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = id,
            onValueChange = { id = it },
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {


            // Load button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 8.dp, end = 8.dp),
                onClick = {
                    // Load data and update UI
                    username = savedUsernameState ?: ""
                    email = savedEmailState ?: ""
                    id = savedIdState ?: ""
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)

                ) {
                Text(
                    text = "Load",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

// Save button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(end = 8.dp),
                onClick = {
                    scope.launch {
                        dataStore.saveDetails(username, email, id)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(
                    text = "Save",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            // Clear button
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(start = 8.dp),
                onClick = {
                    scope.launch {
                        dataStore.clearDetails()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    text = "Clear",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Display saved details
        val savedUsernameState by dataStore.getUsername.collectAsState(initial = "")
        val savedEmailState by dataStore.getEmail.collectAsState(initial = "")
        val savedIdState by dataStore.getId.collectAsState(initial = "")

        val savedUsername = savedUsernameState ?: ""
        val savedEmail = savedEmailState ?: ""
        val savedId = savedIdState ?: ""

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Username: $savedUsername\nEmail: $savedEmail\nCollege ID: $savedId",
                color = Color.Black,
                fontSize = 18.sp
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DataStoreTheme {
        MainScreen()
    }
}
