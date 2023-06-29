package com.example.sample.compose.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sample.compose.ui.component.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavController) {
    Scaffold(topBar = { TopBar(navController = navController, title = "두번째 화면") }) {
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
}

@Preview
@Composable
fun SecondScreenPreview() {
    SecondScreen(navController = rememberNavController())
}
