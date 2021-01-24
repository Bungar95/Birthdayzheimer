package ungar.mvvm.datepicker.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ungar.mvvm.datepicker.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Birthday::class], version = 1)
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
                dao.insert(Birthday("Borna Ungar", 5, 1, 1995))
                dao.insert(Birthday("Mirjana Ungar", 24, 1, 1964))
            }
        }
    }

}