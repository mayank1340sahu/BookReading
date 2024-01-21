package com.example.bookreading.screens.home.widgt

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.bookreading.data.MBook

@Preview
@Composable
fun ListCard(
    book: MBook = MBook("dkd","one piece","Ichiro oda",
    "A kid want to become king of the pirates"),
    onPressDetail:(String) -> Unit = {},
    rating: Double = 4.5) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val spacing = 10.dp
    val screenWidth = displayMetrics.widthPixels/displayMetrics.density
    val actualWidth = screenWidth.dp.minus(spacing*2)
    Card (
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .height(242.dp)
            .padding(16.dp)
            .width(202.dp)
            .clickable { onPressDetail(book.title.toString()) }
    ){
        Column(modifier = Modifier.width(actualWidth)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween){
            Row (  Modifier
                .height(100.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                Image(
                    painter = rememberImagePainter(data = "https://www.bing.com/th?id=OIP.jMfANDS0wBX5OguqpK7MrAHaKR&w=150&h=208&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2"),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(.8f)
                )
                Column(verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "")
                    Card(elevation = CardDefaults.elevatedCardElevation(2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White,
                            contentColor = Color.Black)) {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "")
                        Text(
                            text = "$rating",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column (
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(45.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween){
                Text(text = book.title.toString(), fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
                Text(text = book.author.toString(), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom){
                ReadingButton()
            }
            }
    }
}

@Preview
@Composable
fun ReadingButton(round : Dp = 29.dp) {
    Surface(shape = RoundedCornerShape(topStart = round, bottomEnd = round),
        modifier = Modifier
            .width(100.dp)
            .height(50.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
      Column(verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxSize())  { Text(text = "Reading") }
    }
}
