package com.baransolmaz.rickandmortyapp.features.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.baransolmaz.rickandmortyapp.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.baransolmaz.rickandmortyapp.features.components.CharacterCard
import com.baransolmaz.rickandmortyapp.models.MainViewModel
import com.baransolmaz.rickandmortyapp.ui.theme.*

@Composable
fun HomeScreen(navController: NavHostController?, viewModel: MainViewModel) {
    val characterList by remember { viewModel.characterList }
    val endReached by remember { viewModel.endReached }
    val loadErr by remember { viewModel.loadErr }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlumbusPink),
        contentAlignment = TopCenter
    ){
        Column(
            modifier = Modifier
                .align(TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.rick_and_morty),
                contentDescription ="Logo",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.3f)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .padding(12.dp)
           ){
               viewModel.searchCharacter(it)
           }
            LazyColumn(contentPadding = PaddingValues(10.dp),
                modifier = Modifier.padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(characterList.size){
                        i->
                    if ((i >= (characterList.size - 1)) && !endReached && !isLoading && isSearching){
                        LaunchedEffect(key1 = true){
                            viewModel.loadPage()
                        }
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
}

@Composable
fun SearchBar(
    modifier: Modifier=Modifier,
    hint:String="",
    onSearch: (String) -> Unit = {}
) {
    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplayed by remember{
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier){
        BasicTextField(
            value = text ,
            onValueChange ={
                text=it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color=Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 15.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = ((!it.isFocused) && text.isEmpty())
                }
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 12.dp)
            )
        }
    }

}
/*@Preview
@Composable
fun HomePrev() {
    HomeScreen( null, null)
}*/