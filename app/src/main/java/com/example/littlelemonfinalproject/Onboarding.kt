package com.example.littlelemonfinalproject

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract.Constants
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



@Composable
fun OnboardingScreen( context: Context, navController: NavController? = null){

    val sharedPreferences by lazy {  context.getSharedPreferences("Little Lemon Final Project",
        Context.MODE_PRIVATE)
    }

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value){
        if(imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            Row (Modifier.fillMaxWidth(0.6f)){
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Little Lemon Logo")
            }
            Row (modifier = Modifier
                .height(150.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text("Let's get to know you", modifier = Modifier.padding(16.dp))
            }


            Text(text = "First Name")
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )


            Text(text = "Last Name")
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )


            Text(text = "Email")
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )

            Button(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                        with(sharedPreferences.edit()) {
                            putString("firstName", firstName)
                            putString("lastName", lastName)
                            putString("email", email)
                            putBoolean("userRegistered", true)
                            apply()
                        }
                        Toast.makeText(context,
                            "Registration Successful",
                            Toast.LENGTH_SHORT)
                            .show()

                        navController?.navigate(Home.route) {
                            popUpTo(Onboarding.route){inclusive = true}
                            launchSingleTop = true
                        }


                    }

                    else {
                        Toast.makeText(context,
                            "Invalid Details, Please try again",
                            Toast.LENGTH_SHORT)
                            .show()

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Register")

            }

        }


}


