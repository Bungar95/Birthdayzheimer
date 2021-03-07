package ungar.mvvm.birthdayapp.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

enum class SortOrder { BY_NAME, BY_DATE}

data class OptionsPreferences(val openOptionsCard: Boolean)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val preferencesFlow: Flow<OptionsPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences: ", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val optionsCardOpen = preferences[PreferenceKeys.OPTIONS_CARD_OPEN] ?: false
            val language = preferences[PreferenceKeys.LANGUAGE] ?: "en"
            OptionsPreferences(optionsCardOpen)
        }

    suspend fun updateOptionsCardOpen(optionsCardOpen: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.OPTIONS_CARD_OPEN] = optionsCardOpen
        }
    }

    suspend fun updateLanguage(language: String){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LANGUAGE] = language
        }
    }

    private object PreferenceKeys {
        val OPTIONS_CARD_OPEN = booleanPreferencesKey("options_card_open")
        val LANGUAGE = stringPreferencesKey("app_language")
    }
}
