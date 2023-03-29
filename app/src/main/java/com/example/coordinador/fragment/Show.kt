package com.example.coordinador.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            .header("Conecction", "close")
            .build()

        cliente.newCall(solicitud).enqueue(object : Callback {
            override fun onFailure(call : Call, e : IOException){
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        val body = response.body?.string()
                        val jsonObject = JSONObject(body!!)
                        val dataArray = jsonObject.getJSONArray("data")
                        val lenght = dataArray.length()
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
                            }
                        } while (i < lenght - 1)
                        GlobalScope.launch(Dispatchers.Main) {
                            establecerAdaptador(lista)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun establecerAdaptador(lista : MutableList<Coordinator>){
        recicler = binding.coordinadoresRV
        recicler.layoutManager = LinearLayoutManager(requireActivity())
        recicler.adapter = ListElement(requireActivity(), lista, binding.root)
    }
}