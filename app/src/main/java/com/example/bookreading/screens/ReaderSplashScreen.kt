package com.example.bookreading.screens
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bookreading.navigation.ReaderScreens
import kotlinx.coroutines.delay

@Composable
fun ReaderSplashScreen(navController: NavHostController) {
    val scale = remember{
      Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy,stiffness =Spring.StiffnessLow ),
            )
       delay(800)
        navController.navigate(ReaderScreens.Login.name)
    }
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Surface(
            Modifier
                .scale(scale.value)
                .clip(CircleShape)
                .size(300.dp)
                .border(3.dp, Color.Red, shape = CircleShape)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Book Reading",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFD62E67)
                )
                Text(text = "\"Reading must be a habit\"", color = Color.DarkGray)
            }
        }
    }
}