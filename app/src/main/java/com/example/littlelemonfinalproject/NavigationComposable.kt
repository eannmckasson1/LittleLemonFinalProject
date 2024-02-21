package com.example.littlelemonfinalproject

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun MyNavigation(context: Context ,navController: NavController){
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if(sharedPreferences.getBoolean("userRegistered", false)){
        startDestination = Home.route
    }

    
    NavHost(navController = navController as NavHostController,
        startDestination = startDestination) {
        composable(Home.route){
            Home(navController)
        }
        composable(Onboarding.route){
            OnboardingScreen(context, navController)
        }
        composable(Profile.route){
            Profile(context, navController)
        }
    }

}