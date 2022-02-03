package cz.borsma.zpravodajstvi.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaveGetPreference(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userCategory")
        val CATEGORY_KEY = stringPreferencesKey("user_category")
    }

    //get the saved email
    val getCategory: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[CATEGORY_KEY] ?: "enter"

        }


    //save email into datastore
    suspend fun saveCategory(name: String) {
        context.dataStore.edit { preferences ->
            preferences[CATEGORY_KEY] = name
        }
    }

}