package cz.borsma.zpravodajstvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.borsma.zpravodajstvi.ui.NewsApp
import cz.borsma.zpravodajstvi.ui.screen.SplashScreen
import cz.borsma.zpravodajstvi.ui.theme.ZpravodajstviTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZpravodajstviTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background

                ) {
                    NewsApp()

                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {


}