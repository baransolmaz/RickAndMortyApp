package com.baransolmaz.rickandmortyapp.features.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.baransolmaz.rickandmortyapp.data.filter.Filter
import com.baransolmaz.rickandmortyapp.features.components.CharacterCard
import com.baransolmaz.rickandmortyapp.features.components.FilterItem
import com.baransolmaz.rickandmortyapp.features.components.getAnimateFloat
import com.baransolmaz.rickandmortyapp.models.MainViewModel
import com.baransolmaz.rickandmortyapp.ui.theme.*

@Composable
fun FavoritesScreen(navController: NavHostController, viewModel: MainViewModel) {
    val likedList by remember { viewModel.likedList }
    val isFiltering by remember { viewModel.isFiltering}
    viewModel.likedCharacters()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlumbusPink)
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ){
       val filters by remember{
            mutableStateOf(
                listOf(Filter("Alive"),Filter("Dead"),Filter("Both",true))
            )
        }
        var text:String="Both"
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){

                FilterItem(filters[0]){ txt,isSelected->
                    viewModel.filterCharacters(txt,isSelected)
                    text=txt
                }
                FilterItem(filters[1]){ txt,isSelected->
                    viewModel.filterCharacters(txt,isSelected)
                    text=txt
                }
                FilterItem(filters[2]){ txt,isSelected->
                    viewModel.filterCharacters(txt,isSelected)
                    text=txt
                }
                var isClick by remember { mutableStateOf(false) }
                IconButton(
                    onClick = {
                        viewModel.order(if (isClick){
                            1
                        }else{
                            -1
                        })
                        isClick=!isClick
                    }
                ) {

                    Icon(
                        painter = rememberVectorPainter(image =if(isClick) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp ) ,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            ,//.size(if (isClick) getAnimateFloat().value.dp else 24.dp),
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
                }

            }
            LazyColumn(contentPadding = PaddingValues(10.dp),
                modifier = Modifier.padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(likedList.size){
                        i->
                    //if (likedList[i].isLiked)
                        //if(text=="Both" || likedList[i].status==text)
                            CharacterCard(
                                character =likedList[i],
                                id= i,
                                navController = navController
                            )
                }
            }
        }

    }
}

@Preview
@Composable
fun FavoPrev() {
   // FavoritesScreen(navController = null, viewModel =null )
}