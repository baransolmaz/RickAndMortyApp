package com.baransolmaz.rickandmortyapp.features.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.baransolmaz.rickandmortyapp.features.components.CharacterCard
import com.baransolmaz.rickandmortyapp.models.MainViewModel
import com.baransolmaz.rickandmortyapp.ui.theme.*

@Composable
fun CharacterScreen(navController: NavHostController, viewModel: MainViewModel) {
    val characterList by remember { viewModel.characterList }
    val likedList by remember { viewModel.likedList }
    val endReached by remember { viewModel.endReached }
    val loadErr by remember { viewModel.loadErr }
    val isLoading by remember { viewModel.isLoading }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlumbusPink),
        contentAlignment = Alignment.TopCenter
    ){

        LazyColumn(contentPadding = PaddingValues(10.dp),
            modifier = Modifier.padding(vertical = 5.dp).padding(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(characterList.size){
                    i->
                if (i>= characterList.size-1 && !endReached && !isLoading){
                    viewModel.loadPage()
                }
                CharacterCard(
                    character =characterList[i],
                    id= i,
                    navController = navController
                )

            }
        }
    }
}