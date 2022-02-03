package cz.borsma.zpravodajstvi.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.borsma.zpravodajstvi.BottomMenuScreen
import cz.borsma.zpravodajstvi.components.BottomMenu
import cz.borsma.zpravodajstvi.datastore.GetValue
import cz.borsma.zpravodajstvi.network.NewsManager
import cz.borsma.zpravodajstvi.network.models.TopNewsArticle
import cz.borsma.zpravodajstvi.ui.screen.*
import cz.borsma.zpravodajstvi.viewmodel.VMBottomBar


@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()

    MainScreen(navController = navController, vm = VMBottomBar())
    //SplashScreen(navController = navController)
}



@Composable
fun MainScreen(navController: NavHostController, vm:VMBottomBar) {
    // State of BottomNavigation`s visibility

    val state = remember { mutableStateOf<Boolean>(false) }

    // read the BottomNavigation`s visibility from ViewModel and send to State

    vm.state.observeAsState().value?.let { state.value = it }

    val scrollState = rememberScrollState()
    Scaffold(
        bottomBar ={// show / hide BottomNavigation controlled by State

            state.takeIf { it.value }?.let {
                BottomMenu(navController = navController)
            } })
    {
        Navigation(navController = navController , scrollState =scrollState,paddingValues = it, vm = VMBottomBar() )
    }
}

@Composable
fun TestScreen(navController: NavController,newsManager: NewsManager = NewsManager(), vm: VMBottomBar){


    Scaffold(
    bottomBar ={

            BottomMenu(navController = navController)
        } )
    {
        navController.navigate(BottomMenuScreen.TopNews.route)

    }
}

@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, newsManager: NewsManager = NewsManager(), paddingValues: PaddingValues, vm:VMBottomBar) {

    // State of BottomNavigation`s visibility

    val state = remember { mutableStateOf<Boolean>(false) }

    // read the BottomNavigation`s visibility from ViewModel and send to State

    vm.state.observeAsState().value?.let { state.value = it }

    Scaffold(
        bottomBar ={// show / hide BottomNavigation controlled by State

            state.takeIf { it.value }?.let {
                BottomMenu(navController = navController)
            } }) {

        val articles = mutableListOf(TopNewsArticle(),)
        articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))
        Log.d("newss", "$articles")
        NavHost(
            navController = navController,
            startDestination = "splash_screen",
            modifier = Modifier.padding(paddingValues)
        ) {
            bottomNavigation(navController = navController, articles, newsManager)

            composable("splash_screen") {

                SplashScreen(navController = navController, vm = VMBottomBar())

            }
            composable("main_screen") {
                //TestScreen()
                MainScreen(navController = navController, vm = VMBottomBar())
                //NewsApp()
            }
            composable("test_screen") {
                //TestScreen()
                vm.setState(true)
                TestScreen(navController = navController, vm = VMBottomBar())
                //NewsApp()
            }
            composable("Detail/{index}",
                arguments = listOf(
                    navArgument("index") { type = NavType.IntType }
                )) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    //update the news detail article to include the search response
                    if (newsManager.query.value.isNotEmpty()) {
                        articles.clear()
                        articles.addAll(newsManager.searchedNewsResponse.value.articles ?: listOf())
                    } else {
                        articles.clear()
                        articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>,
                                     newsManager: NewsManager) {
    composable(BottomMenuScreen.TopNews.route) {
        //set the query value from newsmanager as an argument
        //15 pass in newsManager value to TopNews
        TopNews(navController = navController,articles,newsManager.query,newsManager = newsManager)
    }
    composable(BottomMenuScreen.Categories.route) {

        GetValue(newsManager = NewsManager())

        Categories(newsManager = newsManager,onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        })

    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager = newsManager)
    }
}