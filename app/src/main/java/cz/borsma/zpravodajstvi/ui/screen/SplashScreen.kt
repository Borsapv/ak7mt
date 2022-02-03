package cz.borsma.zpravodajstvi.ui.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import cz.borsma.zpravodajstvi.R
import cz.borsma.zpravodajstvi.viewmodel.VMBottomBar
import kotlinx.coroutines.delay

@Composable
//fun SplashScreen(navController: NavHostController) {
fun SplashScreen(navController: NavController, vm: VMBottomBar) {
    //val navController = rememberNavController()
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        // Change the logo
        //Text(text = "TEST OF SPLASH SCREEN", fontWeight = FontWeight.Bold, color = Color.DarkGray)
        Image(painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        // Customize the delay time
        delay(1000L)
        vm.setState(true)
        navController.navigate("test_screen")

    }
    DisposableEffect(key1 = true) {

        onDispose {

            vm.setState(false)

        }
    }

    //MainScreen(navController = navController)
}

