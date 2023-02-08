package com.baransolmaz.rickandmortyapp.features.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.baransolmaz.rickandmortyapp.data.response.SimpleResponse
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse
import com.baransolmaz.rickandmortyapp.features.components.DeadCircle
import com.baransolmaz.rickandmortyapp.models.DetailViewModel
import com.baransolmaz.rickandmortyapp.ui.theme.*
import org.koin.androidx.compose.getViewModel


@Composable
fun DetailsScreen(
    modifier: Modifier=Modifier,
    viewModel:DetailViewModel = getViewModel(),
    id:Int,
    space: Dp =12.dp
){
    val character = produceState<SimpleResponse<SingleCharacterResponse>>(
                initialValue = SimpleResponse.Loading()){
                    value= viewModel.getSingleCharacterByID(id)
                }.value
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RickBlue)
    ){
        CharacterDetailStateWrapper(
            characterResponse = character,
            modifier = Modifier.fillMaxSize(),
            loadModifier = Modifier
        )
        if(character is SimpleResponse.Success) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = modifier.size(space))
                Image(
                    painter = rememberImagePainter(character.data?.image),
                    contentDescription = character.data?.name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(0.35f)
                        .aspectRatio(1f, false)
                        .scale(0.8f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(width = 3.dp, color = Color.Black, RoundedCornerShape(16.dp))
                        .clipToBounds()

                )
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Name", text = character.data?.name!!)
                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Species", text = character.data.species!!)
                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Origin", text = character.data.origin.name!!)
                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Gender", text = character.data.gender!!)
                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Location", text = character.data.location.name!!)
                    Spacer(modifier = modifier.size(space))
                    CreateText(textType = "Episode", text = character.data.episode.size.toString())
                    Spacer(modifier = modifier.size(space))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier.align(CenterHorizontally)
                    ) {
                        DeadCircle(character.data.status!!)
                        Spacer(modifier = modifier.size(12.dp))
                        Text(text = character.data.status, fontSize = 24.sp, color = Color.Black)
                    }
                }
            }
        }

    }
}

@Preview(showSystemUi = false)
@Composable
fun DetailsScreenPrev() {
    RickAndMortyAppTheme {
        DetailsScreen(id=8)
    }
}
@Composable
fun CreateText(modifier: Modifier=Modifier,textType:String,text:String,fontSize:TextUnit=24.sp){
    Row {
        Spacer(modifier = modifier.size(16.dp))
        Text(text = "${textType}:",fontSize = fontSize, color = Color.DarkGray)
        Spacer(modifier = modifier.size(16.dp))
        Text(text =text,fontSize = fontSize, color = Color.Black)
    }
}

@Composable
fun CharacterDetailStateWrapper(
    characterResponse: SimpleResponse<SingleCharacterResponse>,
    modifier: Modifier=Modifier,
    loadModifier: Modifier=Modifier
) {
    when(characterResponse){
        is SimpleResponse.Success->{

        }
        is SimpleResponse.Error->{
            Text(
                text = characterResponse.message!!,
                color=Color.Red,
                modifier = modifier
            )
        }
        is SimpleResponse.Loading->{
            CircularProgressIndicator(
                color= MaterialTheme.colors.primary,
                modifier =loadModifier
            )
        }
    }
}
