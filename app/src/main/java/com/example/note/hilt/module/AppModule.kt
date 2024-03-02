package com.example.note.hilt.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application) : AppDatabase =
        Room.databaseBuilder(application , AppDatabase::class.java , "Note_Database")
            .build()


}