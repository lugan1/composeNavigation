package com.example.sample.compose.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sample.compose.R

sealed class BottomScreen(val route: String, val label: String, val iconRes: Int) {
    object FirstScreen: BottomScreen("first", "홈", R.drawable.ic_home)
    object SecondScreen: BottomScreen("second","블루투스", R.drawable.ic_bluteooth)
    object ForthScreen: BottomScreen("forth", "세팅", R.drawable.ic_setting)

    companion object {
        val navItems = listOf(FirstScreen, SecondScreen, ForthScreen)
    }
}


@Composable
fun FooterNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomScreen.navItems.forEach { bottomScreen ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = bottomScreen.iconRes), contentDescription = bottomScreen.label) },
                label = { Text(bottomScreen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == bottomScreen.route } == true,
                onClick = {
                    navController.navigate(bottomScreen.route) {
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