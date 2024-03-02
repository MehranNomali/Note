package com.example.note.repository

import com.example.note.models.NotesModel
import com.example.note.room.AppDatabase
import com.example.note.room.entities.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    appDatabase: AppDatabase
) {
    private val roomDao = appDatabase.roomDao()

    fun insertNote(noteModel: NotesModel) {
        val noteEntity = NoteEntity(0 , noteModel)
        roomDao.insert(noteEntity)
    }
    fun updateNote(noteEntity: NoteEntity) {
        roomDao.update(noteEntity)
    }
    fun deleteNote(noteEntity: NoteEntity) {
        roomDao.delete(noteEntity)
    }
    fun getAllData(): Flow<List<NoteEntity>>{
        return roomDao.getAll()
    }
}