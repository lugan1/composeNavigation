package com.example.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sample.compose.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "first") {
                //startDestination 은 첫번째 화면을 의미함
                composable("first") { FirstScreen(navController = navController) }
                composable("second") { SecondScreen(navController = navController) }
                composable("third/{value}") { backStackEntry ->
                    ThirdScreen(navController, backStackEntry.arguments?.getString("value") ?: "")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    val (value , setValue) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "첫 화면")
        Spacer(modifier = Modifier.weight(0.1f))
        Button(onClick = { navController.navigate("second") }) {
            Text(text = "두번째 화면 이동")
        }
        Spacer(modifier = Modifier.weight(0.1f))
        TextField(value = value, onValueChange = setValue)
        Button(onClick = {
            if(value.isNotEmpty()) {
                navController.navigate("third/$value") //세번째 화면으로 이동 및 데이터 전달
            }
        }) {
            Text(text = "세번째 화면 이동")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "두번째 화면")
        Spacer(modifier = Modifier.weight(0.1f))
        Button(onClick = {
            navController.navigateUp()
            //navController.popBackStack()
        }) {
            Text(text = "뒤로 가기")
        }
    }
}

@Composable
fun ThirdScreen(navController: NavController, value: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "두번째 화면")
        Spacer(modifier = Modifier.weight(0.1f))
        Text(text = value)
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "뒤로 가기")
        }
    }
}