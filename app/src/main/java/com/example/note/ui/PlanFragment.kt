package com.example.note.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.note.R
import com.example.note.databinding.FragmentPlanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanFragment : Fragment() {
    lateinit var binding : FragmentPlanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_plan , container , false )


        return binding.root
    }

}