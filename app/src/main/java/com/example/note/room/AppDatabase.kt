package com.example.note.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.note.room.entities.NoteEntity

@TypeConverters(NoteTypeConverter::class)
@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun roomDao():RoomDao
}