package com.example.note.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.note.MainActivity
import com.example.note.R
import com.example.note.databinding.FragmentSingleNoteBinding
import com.example.note.models.NotesModel
import com.example.note.room.entities.NoteEntity
import com.example.note.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleNoteFragment : Fragment() {
    private lateinit var binding : FragmentSingleNoteBinding
    private var savedColor = "#64C8FD"
    private val viewModel : AppViewModel by viewModels()
    lateinit var mainActivity : MainActivity
    private lateinit var navController : NavController
    var pinned = false
    var isUpdating = false
    private lateinit var noteEntity: NoteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_single_note , container , false)
        binding.singleNote = this

        getData()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(view)
    }

    private fun getData() {
        if (arguments != null){
            noteEntity = arguments?.getParcelable("datamodel")!!

            binding.titleEdtx.setText(noteEntity.notesModel.title)
            binding.noteEdtx.setText(noteEntity.notesModel.note)

            pinned = noteEntity.notesModel.pinned
            savedColor = noteEntity.notesModel.color

            isUpdating = true

            hideAllCheck()
            colorCheckToVisible(noteEntity.notesModel.color)

        }
    }

    // visible exact color according to card click
    private fun colorCheckToVisible(color: String) {
        binding.apply {
            when (color) {
                "#64C8FD" -> this.check1.visibility = View.VISIBLE
                "#8069FF" -> this.check2.visibility = View.VISIBLE
                "#FFCC36" -> this.check3.visibility = View.VISIBLE
                "#D77FFD" -> this.check4.visibility = View.VISIBLE
                "#FF419A" -> this.check5.visibility = View.VISIBLE
                "#7FFB76" -> this.check6.visibility = View.VISIBLE
            }
        }
    }

    private fun setupToolbar(view: View) {
        navController = Navigation.findNavController(view)
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.singleNoteFragment).build()
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)


        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // for change left icon of toolbar (back Icon)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.singleNoteFragment) {
                toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.singlenote_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pinitem ->
                if (!pinned) {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.baseline_push_pin_black_24dp
                    )
                    pinned = !pinned
                    true
                } else {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.ic_outline_push_pin_24
                    )
                    pinned = !pinned
                    true
                }
            android.R.id.home -> {
                mainActivity.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val item = menu.findItem(R.id.pinitem)
        if(pinned)
            item.icon = ContextCompat.getDrawable(requireActivity() , R.drawable.baseline_push_pin_black_24dp)
        else
            item.icon = ContextCompat.getDrawable(requireActivity() , R.drawable.ic_outline_push_pin_24)
    }
    fun onAddNoteClick(view : View){
        if (isUpdating){
            binding.apply {
                if(this.titleEdtx.text.isNullOrBlank()){
                    Snackbar.make(this.mainCoord , "Please Enter Your Title..." , Snackbar.LENGTH_SHORT).show()
                }else{
                    if (this.noteEdtx.text.isNullOrBlank()){
                        Snackbar.make(this.mainCoord , "Please Enter Your Note..." , Snackbar.LENGTH_SHORT).show()
                    } else{
                        noteEntity.notesModel.title = this.titleEdtx.text.toString()
                        noteEntity.notesModel.note = this.noteEdtx.text.toString()
                        noteEntity.notesModel.color = savedColor
                        noteEntity.notesModel.pinned = pinned

                        viewModel.updateNoteDatabase(noteEntity)

                        Navigation.findNavController(view).navigate(R.id.action_singleNoteFragment_to_homeFragment)
                    }
                }
            }
        }else{
            binding.apply {
                if(this.titleEdtx.text.isNullOrBlank()){
                    Snackbar.make(this.mainCoord , "Please Enter Your Title..." , Snackbar.LENGTH_SHORT).show()
                }else{
                    if (this.noteEdtx.text.isNullOrBlank()){
                        Snackbar.make(this.mainCoord , "Please Enter Your Note..." , Snackbar.LENGTH_SHORT).show()
                    } else{
                        val title = this.titleEdtx.text.toString()
                        val note = this.noteEdtx.text.toString()
                        val color = savedColor

                        val noteModel = NotesModel(title , note , color , pinned)
                        viewModel.insertNoteToDatabase(noteModel)

                        Navigation.findNavController(view).navigate(R.id.action_singleNoteFragment_to_homeFragment)
                    }
                }
            }
        }
    }
    fun onColorViewClick(check : View){
        hideAllCheck()
        check.visibility = View.VISIBLE
        binding.apply {
            when(check.id){
                this.check1.id -> savedColor = "#64C8FD"
                this.check2.id -> savedColor = "#8069FF"
                this.check3.id -> savedColor = "#FFCC36"
                this.check4.id -> savedColor = "#D77FFD"
                this.check5.id -> savedColor = "#FF419A"
                this.check6.id -> savedColor = "#7FFB76"
            }
        }
    }
    fun hideAllCheck(){
        binding.apply {
            check1.visibility = View.INVISIBLE
            check2.visibility = View.INVISIBLE
            check3.visibility = View.INVISIBLE
            check4.visibility = View.INVISIBLE
            check5.visibility = View.INVISIBLE
            check6.visibility = View.INVISIBLE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}