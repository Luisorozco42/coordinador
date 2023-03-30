package com.example.coordinador.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.coordinador.R
import com.example.coordinador.databinding.FragmentHomeBinding

class Home : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var image : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        image = binding.icono

        image.setImageResource(R.mipmap.ic_launcher)
        // Inflate the layout for this fragment
        return binding.root
    }
}