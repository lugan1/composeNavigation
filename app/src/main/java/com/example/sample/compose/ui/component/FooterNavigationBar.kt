package com.example.sample.compose.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

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