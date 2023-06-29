package com.example.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sample.compose.page.FirstScreen
import com.example.sample.compose.page.ForthScreen
import com.example.sample.compose.page.SecondScreen
import com.example.sample.compose.page.ThirdScreen
import com.example.sample.compose.ui.component.FooterNavigationBar
import com.example.sample.compose.ui.component.TopBar

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { FooterNavigationBar(navController = navController) }) { innerPadding ->

                //프래그먼트 컨테이너 역할, 해당 라우트에 맞게 컴포즈를 교체한다
                NavHost(
                    navController = navController,
                    startDestination = "first",
                    Modifier.padding(innerPadding)) {
                    //startDestination 은 첫번째 화면을 의미함
                    composable("first") { FirstScreen(navController = navController) }
                    composable("second") { SecondScreen(navController = navController) }
                    composable("third/{value}") { backStackEntry ->
                        val pathParam = backStackEntry.arguments?.getString("value") ?: ""
                        ThirdScreen(navController, pathParam)
                    }
                    composable("forth") { ForthScreen(navController = navController) }
                }
            }
        }
    }
}