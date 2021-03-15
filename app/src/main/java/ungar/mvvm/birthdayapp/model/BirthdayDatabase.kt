package ungar.mvvm.birthdayapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Birthday::class, Note::class], version = 4)
abstract class BirthdayDatabase: RoomDatabase() {

    abstract fun birthdayDao(): BirthdayDao

    class Callback @Inject constructor(
        private val database: Provider<BirthdayDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().birthdayDao()

            applicationScope.launch {
                //dao.insert(Birthday("Testy Test", 20, 1, 1960))
            }
        }
    }

}