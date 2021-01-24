package ungar.mvvm.datepicker.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ungar.mvvm.datepicker.model.BirthdayDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton //We only need 1 instance of dao and database so we use singleton
    fun provideDatabase(
        app: Application,
        callback: BirthdayDatabase.Callback
    ) = Room.databaseBuilder(app, BirthdayDatabase::class.java, "birthday_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideTaskDao(db: BirthdayDatabase) = db.birthdayDao()

    //create a scope for coroutine
    //supervisor tells the coroutine to continue other child if a child fails
    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

// ? need to check this more
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope