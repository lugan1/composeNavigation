package com.example.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Scaffold(bottomBar = { FooterNavigationBar(navController = navController) }) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "first",
                    Modifier.padding(innerPadding)) {
                    //startDestination 은 첫번째 화면을 의미함
                    composable("first") { FirstScreen(navController = navController) }
                    composable("second") { SecondScreen(navController = navController) }
                    composable("third/{value}") { backStackEntry ->
                        ThirdScreen(navController, backStackEntry.arguments?.getString("value") ?: "")
                    }
                    composable("forth") { ForthScreen(navController = navController) }
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
        Text(text = "세번째 화면")
        Spacer(modifier = Modifier.weight(0.1f))
        Text(text = value)
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "뒤로 가기")
        }
    }
}

@Composable
fun ForthScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "네번째 화면")
        Spacer(modifier = Modifier.weight(0.1f))
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "뒤로 가기")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FooterNavigationBar(navController: NavController) {
    val navItems = listOf("first", "second", "forth")
    var selectedItemIndex by remember { mutableStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                label = { Text(item) },
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item) {
                        //그래프의 시작 지점까지 팝업하여, 사용자가 항목을 선택함에 따라 백 스택에 대량의 항목이 쌓이지 않도록 방지한다.
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }

                        //동일한 항목을 다시 선택할 때, 동일한 목적지의 새 인스턴스를 생성하지 않고, 현재 목적지를 다시 시작한다. (예를 들어, 홈 버튼)
                        launchSingleTop = true

                        //이전에 선택했던 항목을 다시 선택할 때, 해당 항목의 상태를 복원한다. (예를 들어, 스크롤 위치)
                        restoreState = true
                    }
                })
        }
    }
}

@Preview
@Composable
fun FooterNavigationBarPreview() {
    FooterNavigationBar(navController = rememberNavController())
}


@Preview
@Composable
fun FirstScreenPreview() {
    FirstScreen(navController = rememberNavController())
}

@Preview
@Composable
fun SecondScreenPreview() {
    SecondScreen(navController = rememberNavController())
}

@Preview
@Composable
fun ThirdScreenPreview() {
    ThirdScreen(navController = rememberNavController(), value = "test")
}

@Preview
@Composable
fun ForthScreenPreview() {
    ForthScreen(navController = rememberNavController())
}

