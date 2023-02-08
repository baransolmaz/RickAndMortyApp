package com.baransolmaz.rickandmortyapp.features.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baransolmaz.rickandmortyapp.data.filter.Filter
import com.baransolmaz.rickandmortyapp.models.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun FilterItem(
    filter:Filter,
    viewModel: MainViewModel= getViewModel(),
    onFilter: (String,Boolean) -> Unit = { text: String, isSelected: Boolean -> }
) {
    var isClick by remember { mutableStateOf(false) }
    LaunchedEffect(isClick) {
        if (isClick) {
            delay(500)
            isClick = false
        }
    }
    TextButton(
        onClick = {
            isClick=true
            filter.isSelected = !filter.isSelected
            onFilter(filter.value,filter.isSelected)
            viewModel.filterCharacters(filter.value,filter.isSelected)
        }
    ) {
        Box(
            modifier = Modifier
                //.border(3.dp, Color.Black, RoundedCornerShape(15.dp))
                .background(
                    color = if(filter.isSelected){
                        Color.LightGray
                    }else
                        Color.DarkGray,
                    shape = RoundedCornerShape(20f))
            ,
        ) {
            Row {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = filter.value.uppercase(Locale.ROOT), fontSize = 20.sp, color = Color.Black)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }


}
@Preview
@Composable
fun PrevK() {
    FilterItem(Filter("alive",false))
}