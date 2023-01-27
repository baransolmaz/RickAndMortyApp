package com.baransolmaz.rickandmortyapp.features.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baransolmaz.rickandmortyapp.R
import com.baransolmaz.rickandmortyapp.data.Character
import com.baransolmaz.rickandmortyapp.data.SingleCharacterResponse

@Composable
fun CharacterCard(painter: Painter,character:SingleCharacterResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, Color.Black, RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Row (

            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ){
            Image(
                painter =painter,
                contentDescription ="X",
                modifier=Modifier.background(Color.Transparent)
            )
            Column(
                modifier = Modifier.fillMaxWidth().height(painter.intrinsicSize.height.dp/3),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = character.name, fontSize = 22.sp)
                Text(text = character.species, fontSize = 18.sp, color = Color.DarkGray)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)

                ) {
                    DeadCircle(character.status)
                    Text(
                        text = character.status,
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }

}
@Preview
@Composable
private fun BodyPreview() {
    CharacterCard(
        painter = painterResource(id = R.drawable.rick_and_morty),
        SingleCharacterResponse(name = "Rick Sanchez", status = "Alive", species = "Human")
    )
}
@Composable
fun DeadCircle(isAlive:String="Alive"){
    Canvas(modifier = Modifier.size(9.dp), onDraw = {
        drawCircle(color = if (isAlive=="Alive")
                                Color.Green
                            else
                                Color.Red
                )
    })
}