package com.example.note.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.R
import com.example.note.databinding.PinnedRvItemsBinding
import com.example.note.room.entities.NoteEntity

class PinnedRVAdapter(private var data: ArrayList<NoteEntity> , private val listener : CardClickListener) : RecyclerView.Adapter<PinnedRVAdapter.PinnedRvViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PinnedRVAdapter.PinnedRvViewHolder {
        val pinnedRvItemsBinding : PinnedRvItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.pinned_rv_items , parent ,false)
        return PinnedRvViewHolder(pinnedRvItemsBinding)
    }

    override fun onBindViewHolder(holder: PinnedRVAdapter.PinnedRvViewHolder, position: Int) {
        holder.bind(data[position] , listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class PinnedRvViewHolder(private val binding : PinnedRvItemsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(noteEntity: NoteEntity, listener: CardClickListener) {
            binding.pinnedtitle.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    noteEntity.notesModel.title ,
                    binding.pinnedtitle.textMetricsParamsCompat ,
                    null
                )
            )
            binding.pinneddescription.text = noteEntity.notesModel.note
            binding.pinnedcardview.setCardBackgroundColor(Color.parseColor(noteEntity.notesModel.color))
            binding.pinnedcardview.setOnClickListener {
                listener.onItemClickListener(noteEntity)
            }
            binding.imageFilterButton.setOnClickListener{
                listener.onMenuItemClickListener(it , noteEntity)
            }
            binding.executePendingBindings()
        }

    }
}