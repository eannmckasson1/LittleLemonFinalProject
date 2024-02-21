package com.example.littlelemonfinalproject

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Profile(context: Context, navController: NavController? = null){
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf(sharedPreferences.getString("firstName", ""))
    }
    val lastName = remember {
        mutableStateOf(sharedPreferences.getString("lastName", ""))
    }
    val email = remember {
        mutableStateOf(sharedPreferences.getString("email", ""))
    }

    val scrollState = rememberScrollState()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),

        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Header()
        Text(
            text = "Profile information:",
            modifier = Modifier.padding(bottom = 16.dp)
        )



        Column {
            Text(text = "First Name: $firstName")
            Text(text = "Last Name: $lastName")
            Text(text = "Email: $email")
        }

        Button(onClick = {
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }
            navController?.navigate(Onboarding.route)
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(text = "Log Out")

        }
    }


}


