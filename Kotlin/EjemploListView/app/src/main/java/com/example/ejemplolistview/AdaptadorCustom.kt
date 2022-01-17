package com.example.ejemplolistview

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


//El adaptador se encarga de crear los elementos a agregar a la lista
class AdaptadorCustom(var context:Context, items:ArrayList<Fruta> ):BaseAdapter(){
    var items:ArrayList<Fruta>? = null

    init {
        this.items = items
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null
        var vista:View? = convertView


        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.template, null)

            holder = ViewHolder(vista)

            vista.tag = holder
        }else{
            holder = vista.tag as? ViewHolder
        }

        val item =getItem(position) as Fruta
        holder?.nombre?.text = item.nombre
        holder?.imagen?.setImageResource(item.imagen)

        if (position %2 == 0)
            holder?.celda?.setBackgroundColor(Color.GRAY)

        holder?.boton?.setOnClickListener{
            Toast.makeText(vista?.context, holder.nombre?.text, Toast.LENGTH_LONG).show()
        }

        return vista!!
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    override fun getItem(position: Int): Any {
    return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
    return position.toLong()
    }

    //El holder es la "estructura de elementos de la vista" que vamos a utilizar
    private class ViewHolder(vista:View){
        var nombre:TextView?=null
        var imagen:ImageView? = null
        var celda:LinearLayout? = null
        var boton:Button? = null

        init {
            nombre = vista.findViewById(R.id.nombre)
            imagen = vista.findViewById(R.id.imagen)
            celda = vista.findViewById(R.id.celda)
            boton = vista.findViewById(R.id.boton)
        }
    }


}
