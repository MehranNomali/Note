package com.example.note.adapter

import android.view.View
import com.example.note.room.entities.NoteEntity

interface CardClickListener {
    fun onItemClickListener(noteEntity: NoteEntity)
    fun onMenuItemClickListener(imageFilterButton : View , noteEntity: NoteEntity)
}