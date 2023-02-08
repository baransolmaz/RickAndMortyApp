package com.baransolmaz.rickandmortyapp.features.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.baransolmaz.rickandmortyapp.R
import com.baransolmaz.rickandmortyapp.data.response.SingleCharacterResponse
import com.baransolmaz.rickandmortyapp.models.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun CharacterCard(
    painter: Painter = painterResource(id = R.drawable.rick_and_morty),
    character: SingleCharacterResponse,
    id:Int=0,
    navController: NavController?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(3.dp, Color.Black, RoundedCornerShape(15.dp))
            .clickable {
                navController!!.navigate(
                    "details/${character.id}"
                )
            },
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        backgroundColor = Color.LightGray
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ){
            Image(
                painter= rememberImagePainter(character.image),
                contentDescription =character.name,
                contentScale = ContentScale.Crop,
                modifier= Modifier
                    .background(Color.Transparent)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .border(width = 1.dp, color = Color.Black, RoundedCornerShape(16.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(painter.intrinsicSize.height.dp / 3)
                    .padding(vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${id+1}. ${character.name!!}",
                        fontSize = 20.sp,
                        color=Color.Black,
                    )
                }
                Text(text = character.species!!, fontSize = 16.sp, color = Color.DarkGray)
                Row(
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)

                ) {
                    character.status?.let { DeadCircle(it) }
                    character.status?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = character.episode.size.toString(),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }
            var isClick by remember { mutableStateOf(false) }
            LaunchedEffect(isClick) {
                if (isClick) {
                    delay(800)
                    isClick = false
                }
            }
            IconButton(
                onClick = {
                    isClick=true
                    character.isLiked = !character.isLiked
                }
            ) {
                val color = if (character.isLiked) Color.Red else Color.Gray

                Icon(
                    modifier = Modifier
                        .align(CenterVertically)
                        .fillMaxWidth()
                        .height(painter.intrinsicSize.height.dp / 4)
                        .size(if (isClick) getAnimateFloat().value.dp else 24.dp),
                    painter = rememberVectorPainter(Icons.Default.Favorite),
                    contentDescription = null,
                    tint = color
                )
            }

        }
    }

}
@Composable
fun getAnimateFloat(): State<Float> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateFloat(
        initialValue = 24.0f,
        targetValue = 48.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                delayMillis = 100,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
}
@Composable
fun DeadCircle(isAlive:String="Alive"){
    Canvas(modifier = Modifier.size(9.dp), onDraw = {
        drawCircle(color = when (isAlive) {
                                "Alive" -> Color.Green
                                "Dead" -> Color.Red
                                else -> Color.Gray
                            }
                )
    })
}

@Preview
@Composable
fun Prev() {
    CharacterCard(character = SingleCharacterResponse(), navController = null)
}