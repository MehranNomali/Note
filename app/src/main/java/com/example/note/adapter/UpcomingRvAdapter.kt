package com.example.note.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.databinding.UpcomingRvItemsBinding
import com.example.note.room.entities.NoteEntity

class UpcomingRvAdapter(private val model: ArrayList<NoteEntity> , private val listener : CardClickListener) :RecyclerView.Adapter<UpcomingRvAdapter.UpcomingRvViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingRvViewHolder {
        val upcomingRvItemsBinding: UpcomingRvItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.upcoming_rv_items,parent,false)
        return UpcomingRvViewHolder(upcomingRvItemsBinding)
    }

    override fun onBindViewHolder(holder: UpcomingRvViewHolder, position: Int) {
        holder.bind(model[position] , listener)
    }


    override fun getItemCount() = model.size

    class UpcomingRvViewHolder(private val binding: UpcomingRvItemsBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(noteEntity: NoteEntity, listener: CardClickListener) {


            binding.upcomingCard.setCardBackgroundColor(Color.parseColor(noteEntity.notesModel.color))
            binding.pinnedtitle.text = noteEntity.notesModel.title
            binding.pinneddescription.text = noteEntity.notesModel.note
            binding.upcomingCard.setOnClickListener {
                listener.onItemClickListener(noteEntity)
            }
            binding.imageFilterButton2.setOnClickListener{
                listener.onMenuItemClickListener(it , noteEntity)
            }
            binding.executePendingBindings()
        }
    }
}