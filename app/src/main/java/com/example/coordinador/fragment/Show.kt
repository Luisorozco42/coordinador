package com.example.coordinador.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinador.databinding.FragmentShowBinding
import com.example.coordinador.entity.Coordinator
import com.example.coordinador.reciclerView.ListElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class Show : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentShowBinding
    private lateinit var recicler : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentShowBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        recieveCoordinator()
        return binding.root
    }

    fun recieveCoordinator(){
        var cont : Int = 0
        val lista : MutableList<Coordinator> = mutableListOf()
        val cliente = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val url = "http://192.168.1.4/coordinator/showCoordinator.php"
        val solicitud : Request = Request.Builder()
            .url(url)
            .header("Connection", "close")
            .build()

        cliente.newCall(solicitud).enqueue(object : Callback {
            override fun onFailure(call : Call, e : IOException){ // Funcion por si falla la conexion
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error en la conexión", Toast.LENGTH_SHORT).show()}
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        val body = response.body?.string() // Se recibe el cuerpo de los datos
                        val jsonObject = JSONObject(body!!) //Se le asigna a un objeto JSON
                        val dataArray = jsonObject.getJSONArray("data") // Se genera un arreglo a partir de la palabra data que almacena los datos de las columnas
                        val lenght = dataArray.length()// Se recibe el largo del arreglo
                        var i = 0
                        do {
                            if (lenght != 0) {
                                val coordinadorJSON= dataArray.getJSONObject(i)
                                val idC: Int = coordinadorJSON.getString("idC").toInt()
                                val nombre: String = coordinadorJSON.getString("nombre")
                                val apellido: String = coordinadorJSON.getString("apellido")
                                val fechaNac: String = coordinadorJSON.getString("fechaNac")
                                val titulo = coordinadorJSON.getString("titulo")
                                val email = coordinadorJSON.getString("email")
                                val facultad = coordinadorJSON.getString("facultad")
                                var coordinator : Coordinator = Coordinator(idC, nombre, apellido, fechaNac, titulo, email, facultad)
                                lista.add(coordinator)
                                i++
                            }else Toast.makeText(requireContext(), "La base de datos esta vacia", Toast.LENGTH_SHORT).show()
                        } while (i < lenght - 1)// Se realiza la asignacion mientras el arreglo aun tenga datos que no se han guardado
                        GlobalScope.launch(Dispatchers.Main) {
                            establecerAdaptador(lista)// Se envian los datos al recyclerView
                            Toast.makeText(requireContext(), "Los datos se han recibido correctamente", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), "Error al recibir los datos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    fun establecerAdaptador(lista : MutableList<Coordinator>){ // Funcion para crear las cardView necesarias
        recicler = binding.coordinadoresRV//Se obtiene el reciclerView
        recicler.layoutManager = LinearLayoutManager(requireActivity()) // Se le asigna la vista del fragment
        recicler.adapter = ListElement(requireActivity(), lista, binding.root) // Se envian el contexto de la actividad, la mutable list y la vista en que se pondra
    }
}