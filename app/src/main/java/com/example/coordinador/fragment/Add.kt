package com.example.coordinador.fragment

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.coordinador.databinding.FragmentAddBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*

class Add : Fragment() {
    private lateinit var binding : FragmentAddBinding
    private lateinit var editText : TextInputEditText
    private lateinit var enviar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentAddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editText = binding.txtFechaNac
        enviar = binding.btnGuardar

        editText.setOnClickListener{
            seleccionarFecha()
        }

        enviar.setOnClickListener{
            if(
                binding.txtNombre.toString().isNotEmpty()
                && binding.txtApellido.toString().isNotEmpty()
                && binding.txtFechaNac.toString().isNotEmpty()
                && binding.txtTitulo.toString().isNotEmpty()
                && binding.txtEmail.toString().isNotEmpty()
                && binding.txtFacultad.toString().isNotEmpty()
            )enviarDatos() else Toast.makeText(requireContext(), "Hay campos vacios, llene todos los datos por favor", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    fun seleccionarFecha(){
        val calendar = Calendar.getInstance()
        val datelistener = DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            editText.setText(dateFormat.format(calendar.time))
            println(editText.text.toString())
        }
        DatePickerDialog(requireContext(), datelistener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun enviarDatos(){
        val requestbody : RequestBody = FormBody.Builder()
            .add("nombre", binding.txtNombre.text.toString())
            .add("apellido", binding.txtApellido.text.toString())
            .add("fechaNac", binding.txtFechaNac.text.toString())
            .add("titulo", binding.txtTitulo.text.toString())
            .add("email", binding.txtEmail.text.toString())
            .add("facultad", binding.txtFacultad.text.toString())
            .build()

        val solicitud = Request.Builder()
            .url("http://192.168.1.4/coordinator/insertCoordinator.php")
            .post(requestbody)
            .build()

        GlobalScope.launch(Dispatchers.IO){
            val cliente : OkHttpClient = OkHttpClient()
            val respuesta : Response = cliente.newCall(solicitud).execute()

            if (respuesta.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Se enviaron los datos correctamente", Toast.LENGTH_SHORT).show()
                    binding.txtNombre.setText("")
                    binding.txtApellido.setText("")
                    binding.txtFechaNac.setText("")
                    binding.txtTitulo.setText("")
                    binding.txtEmail.setText("")
                    binding.txtFacultad.setText("")
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al enviar los datos", Toast.LENGTH_SHORT).show()
                }
        }
        }
    }
}