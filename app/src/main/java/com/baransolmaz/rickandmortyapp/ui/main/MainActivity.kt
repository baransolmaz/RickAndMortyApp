package com.baransolmaz.rickandmortyapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.baransolmaz.rickandmortyapp.data.navigation.BottomNavItem
import com.baransolmaz.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.baransolmaz.rickandmortyapp.features.components.BottomNavigationBar
import com.baransolmaz.rickandmortyapp.features.components.Navigation


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Characters",
                                    route = "characters",
                                    icon = Icons.Default.Person,
                                ),
                                BottomNavItem(
                                    name = "Favorites",
                                    route = "favorites",
                                    icon = Icons.Default.Favorite,
                                )
                            ),
                            navController =navController ,
                            onItemClick ={
                                navController.navigate(it.route)
                            } )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }


        }
    }
}


@Preview
@Composable
fun PrevCard() {

}
