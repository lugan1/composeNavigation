package com.example.sample.compose.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sample.compose.ui.component.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    val (value , setValue) = remember { mutableStateOf("") }

    Scaffold(topBar = {TopBar(navController = navController, title = "첫 화면")}) {
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
}

@Preview
@Composable
fun FirstScreenPreview() {
    FirstScreen(navController = rememberNavController())
}
