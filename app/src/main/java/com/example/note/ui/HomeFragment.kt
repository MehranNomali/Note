package com.example.note.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.note.R
import com.example.note.adapter.CardClickListener
import com.example.note.adapter.PinnedRVAdapter
import com.example.note.adapter.UpcomingRvAdapter
import com.example.note.databinding.FragmentHomeBinding
import com.example.note.room.entities.NoteEntity
import com.example.note.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),CardClickListener {

    private val viewModel: AppViewModel by viewModels()

    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        binding.fragmentHome = this

        setupPinnedRecyclerview()
        setupUpcomingRecyclerview()


        return binding.root
    }



    private fun setupUpcomingRecyclerview() {
        viewModel.liveData.observe(viewLifecycleOwner){ listData ->

            val data: ArrayList<NoteEntity> = ArrayList()
            listData.forEach{
                if (!it.notesModel.pinned){
                    data.add(it)
                }
            }

            if (data.isEmpty())
                binding.textView3.visibility = View.VISIBLE
            else
                binding.textView3.visibility = View.GONE


            binding.upcomingRv.setHasFixedSize(true)
            binding.upcomingRv.adapter = UpcomingRvAdapter(data , this)



        }
    }

    private fun setupPinnedRecyclerview() {
        viewModel.liveData.observe(viewLifecycleOwner){ listData ->
            val data : ArrayList<NoteEntity> = ArrayList()
            listData.forEach{
                if (it.notesModel.pinned){
                    data.add(it)
                }
            }

            if (data.isEmpty())
                binding.pinnedCon.visibility = View.GONE
            else
                binding.pinnedCon.visibility = View.VISIBLE

            binding.pinnedRv.adapter = PinnedRVAdapter(data , this)

        }

    }
    fun fabOnClick(view : View){
        view.findNavController().navigate(R.id.action_homeFragment_to_singleNoteFragment)
    }

    override fun onItemClickListener(noteEntity: NoteEntity) {
        val bundle = bundleOf("datamodel" to noteEntity)
        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_singleNoteFragment,bundle)
    }

    override fun onMenuItemClickListener(view : View , noteEntity: NoteEntity) {
        val popupMenu = PopupMenu(requireActivity() , view)
        popupMenu.setOnMenuItemClickListener (PopupMenu.OnMenuItemClickListener {
            when(it.itemId){
                R.id.delete -> {
                    deleteNoteFromDb(noteEntity)
                    true
                }
                else -> return@OnMenuItemClickListener false
            }
        })
        popupMenu.inflate(R.menu.actions)
        popupMenu.show()
    }
    fun deleteNoteFromDb(noteEntity: NoteEntity){
        viewModel.deleteNote(noteEntity)
    }
}