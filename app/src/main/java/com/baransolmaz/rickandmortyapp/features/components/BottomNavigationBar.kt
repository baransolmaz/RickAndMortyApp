package com.baransolmaz.rickandmortyapp.features.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.baransolmaz.rickandmortyapp.MainActivity.Companion.characters
import com.baransolmaz.rickandmortyapp.MainActivity.Companion.viewModel
import com.baransolmaz.rickandmortyapp.MainViewModel
import com.baransolmaz.rickandmortyapp.R
import com.baransolmaz.rickandmortyapp.data.BottomNavItem
import com.baransolmaz.rickandmortyapp.ui.theme.*
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavigationBar(
    items:List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier=Modifier,
    onItemClick:(BottomNavItem)->Unit
) {
    val backStackEntry=navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .border(
                width = 3.dp,
                shape =RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                color = RickBlue
            ),
        backgroundColor = MortyYellow,
        elevation = 5.dp,

    ){
        items.forEach{item->
            val selected= item.route== backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = item.route==navController.currentDestination?.route,
                onClick = {
                    navController.navigate(item.route){
                        navController.graph.startDestinationRoute?.let {
                            route->popUpTo(route){
                                saveState=true
                            }
                        }
                        launchSingleTop = true
                        restoreState=true
                    }
                    //onItemClick(item)
                },
                selectedContentColor = PortalGreen,
                unselectedContentColor = Solenya,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        if(selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}
@Composable
fun Navigation(navController:NavHostController){
    NavHost(navController = navController, startDestination ="home" ){
        composable("home"){
            HomeScreen()
        }
        composable("characters"){
            CharacterScreen()
        }
        composable("favorites"){
            FavoritesScreen()
        }


    }
}
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlumbusPink),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Home Screen")
    }
}
@Composable
fun FavoritesScreen() {
    val characters= characters.subList(8,56)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(PlumbusPink),
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(contentPadding = PaddingValues(10.dp),
            modifier = Modifier.padding(vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(characters){
                    character->
                CharacterCard(painter = painterResource(id = R.drawable.rick_and_morty) , character =character )
            }
        }
    }
}
/*
@Preview
@Composable
fun HomeScreenPrev() {
    HomeScreen()
}*/
@Composable
fun CharacterScreen() {
    val characters= characters
    Box(
        modifier = Modifier.fillMaxSize()
            .background(PlumbusPink),
        contentAlignment = Alignment.TopCenter
    ){

        LazyColumn(contentPadding = PaddingValues(10.dp),
            modifier = Modifier.padding(vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(characters){
                    character->
                CharacterCard(painter = painterResource(id = R.drawable.rick_and_morty) , character =character )
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showSystemUi = false)
@Composable
fun BottomNavBarPreview() {
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
                            icon = Icons.Default.Person
                        ),
                        BottomNavItem(
                            name = "Favorites",
                            route = "favorites",
                            icon = Icons.Default.Favorite
                        )),
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
