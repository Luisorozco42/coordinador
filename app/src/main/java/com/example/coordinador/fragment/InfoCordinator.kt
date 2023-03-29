package com.example.coordinador.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.coordinador.databinding.FragmentInfoCordinatorBinding
import com.example.coordinador.entity.Coordinator


class InfoCordinator : Fragment() {

    private lateinit var binding : FragmentInfoCordinatorBinding
    private lateinit var cordinador : Coordinator
    //Variable que recibe datos del
    private val args : InfoCordinatorArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentInfoCordinatorBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Es importante tener todas las variables inicializadas
        cordinador = args.cordinador

        // Poniendo los datos dentro de de los text views
        binding.idC.text = cordinador.idC.toString()
        binding.nombre.text = cordinador.nombre
        binding.apellido.text = cordinador.apellido
        binding.fechaNac.text = cordinador.fechaNac
        binding.titulo.text = cordinador.titulo
        binding.email.text = cordinador.email
        binding.facultad.text = cordinador.facultad

        //Aqui inicia el editar en un futuro

        return binding.root
    }
}