package com.example.coordinador.reciclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coordinador.R
import com.example.coordinador.entity.Coordinator
import com.example.coordinador.fragment.InfoCordinator
import com.example.coordinador.fragment.ShowDirections

class ListElement (var context : Context,
                   var listCoordinator : MutableList<Coordinator>, var vista : View
):RecyclerView.Adapter<ListElement.MyHolder>(){
    inner class MyHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var cNombre : TextView
        var cApellido : TextView
        var cCorreo : TextView

        init{
            cNombre = itemView.findViewById(R.id.txtnombre)
            cApellido = itemView.findViewById(R.id.txtApellido)
            cCorreo = itemView.findViewById(R.id.txtcorreo)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.list_coordinator, parent, false)
        return MyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listCoordinator.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var coordinador = listCoordinator[position]
        holder.cNombre.text = coordinador.nombre
        holder.cApellido.text = coordinador.apellido
        holder.cCorreo.text = coordinador.email

        holder.itemView.setOnClickListener{
            val action = ShowDirections.actionShowToInfoCordinator(coordinador)
            Navigation.findNavController(vista).navigate(action)}
    }
}