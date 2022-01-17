package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class AdaptadorCustom(var context: Context, items:ArrayList<Contacto>): BaseAdapter() {
    var items:ArrayList<Contacto>? = null
    //Copia de elementos para no peder los elementos al realizar la busqueda de contactos
    var copiaItems:ArrayList<Contacto>? = null

    init {
        //Este tiene los mismos elementos que items
        this.items = ArrayList(items)
        //Este es el mismo arreglo items
        copiaItems = items
    }

    override fun getCount(): Int {
        return items?.count()!!

    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addItem(item:Contacto){
        copiaItems?.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index: Int, item: Contacto){
        copiaItems?.set(index, item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder? = null
        var vista:View? =convertView

        if (vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto, null)

            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        }else{
            viewHolder = vista.tag as? ViewHolder
        }

        val item = getItem(position) as Contacto
        //Asignacion de valores a elementos graficos
        viewHolder?.nombre?.text = item.nombre + " " + item.apellido
        viewHolder?.estado?.text = item.estado
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    fun filtar(str:String){
        items?.clear()

        //Si no busco nada muestro la lista completa
        if (str.isEmpty()){
            items = ArrayList(copiaItems)
            //Vuelve a renderizar y return me saca de la funcion
            notifyDataSetChanged()
            return
        }
        var busqueda = str
        //paso a minusculas
        busqueda = busqueda.toLowerCase()
        for (item in copiaItems!!){
            val nombre = item.nombre.toLowerCase() + " " + item.apellido.toLowerCase()

            if (nombre.contains(busqueda))
                items?.add(item)
        }
        //Vuele a renderizar
        notifyDataSetChanged()
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var estado:TextView? = null
        var foto:ImageView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombreTemplContact)
            estado = vista.findViewById(R.id.tvEstadoTemplateContact)
            foto = vista.findViewById(R.id.ivFotoTemplateContac)
        }
    }
}