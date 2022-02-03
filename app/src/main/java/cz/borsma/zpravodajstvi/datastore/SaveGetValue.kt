package cz.borsma.zpravodajstvi.datastore

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import cz.borsma.zpravodajstvi.network.NewsManager
import cz.borsma.zpravodajstvi.datastore.SaveGetPreference
import cz.borsma.zpravodajstvi.ui.screen.Categories

@Composable


//write selected category into datastore
fun SaveValue(category: String){
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the saveEmail class
    val dataStore = SaveGetPreference(context)


    scope.launch {
        dataStore.saveCategory(category)
        Log.d("CATT","Saving category - $category")
    }
}

//read data from datastore
@Composable
fun GetValue(newsManager: NewsManager) {
    val context = LocalContext.current
    val dataStore = SaveGetPreference(context)

    val userCategory = dataStore.getCategory.collectAsState(initial = "")


    val categoryValue = userCategory.value!!
    

    newsManager.getArticlesByCategory(categoryValue)
    newsManager.onSelectedCategoryChanged(categoryValue)

    Log.d("CATT2","get $categoryValue")


}


