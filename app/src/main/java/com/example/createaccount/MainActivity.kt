package com.example.createaccount

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.createaccount.ui.theme.CreateAccountTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateAccountTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateAccountScreen()
                }
            }
        }
    }
}

@Composable
fun CreateAccountScreen() {
    val context = LocalContext.current

    // Remember state for all inputs
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var agreed by rememberSaveable { mutableStateOf(false) }

    // Dark / Light mode toggle
    var isDarkMode by rememberSaveable { mutableStateOf(false) }

    val gradientBackground = if (isDarkMode) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF2C3E50), Color(0xFF4CA1AF))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0xFFB3E5FC), Color(0xFF81D4FA))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🌗 Light/Dark Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(if (isDarkMode) "Dark Mode" else "Light Mode")
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it }
            )
        }

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // First & Last Name
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password (selalu tersembunyi)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Gender:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = gender == "Male",
                onClick = { gender = "Male" }
            )
            Text(text = "Male")
            Spacer(modifier = Modifier.width(12.dp))
            RadioButton(
                selected = gender == "Female",
                onClick = { gender = "Female" }
            )
            Text(text = "Female")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agreed,
                onCheckedChange = { agreed = it }
            )
            Text("I agree to the Terms and Conditions")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    firstName = ""
                    lastName = ""
                    username = ""
                    password = ""
                    gender = ""
                    agreed = false
                    isDarkMode = false
                }
            ) {
                Text("Clear")
            }

            Button(
                onClick = {
                    if (firstName.isNotBlank() && username.isNotBlank() && gender.isNotBlank() && agreed) {
                        Toast.makeText(
                            context,
                            "Hello $firstName $lastName!\nUsername: $username\nGender: $gender",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all fields and agree to terms.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text("Submit")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreviewLight() {
    CreateAccountTheme(darkTheme = false) {
        CreateAccountScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreviewDark() {
    CreateAccountTheme(darkTheme = true) {
        CreateAccountScreen()
    }
}
