package com.example.ejemplorecyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCustom(items: ArrayList<Platillo>, var listener: ClickListener, var longClickListener:LongClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items:ArrayList<Platillo>? = null
    var multiSeleccion = false
    var viewHolder:ViewHolder? = null
    //Este array almacena los indices de los items seleccionados
    var itemsSeleccionados:ArrayList<Int>? = null

    init {
        this.items = items
        itemsSeleccionados = ArrayList()
    }

//Crea el viewHolder y mete la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.template_platillo, parent, false)
        viewHolder = ViewHolder(vista, listener, longClickListener)

        return viewHolder!!
    }

    //Mapeo los elementos
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //obtengo el elemento actual
        val item = items?.get(position)
        holder.imagen?.setImageResource(item?.imagen!!)
        holder.nombre?.text = item?.nombre
        holder.precio?.text = "$ " + item?.precio.toString()
        holder.rating?.rating = item?.rating!!
        //Si el item esta en la lista de elementos seleccionados se pone un fondo gris
        if (itemsSeleccionados?.contains(position)!!){
            holder.vista.setBackgroundColor(Color.LTGRAY)
        }else{
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    fun iniciarActionMode() {
        multiSeleccion = true
    }

    fun destruirActionMode() {
        multiSeleccion = false
        itemsSeleccionados?.clear()
        notifyDataSetChanged()
    }

    fun terminarActionMode() {
        //Eliminar elementos selseccionados
        for (item in itemsSeleccionados!!){
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion = false
        notifyDataSetChanged()
    }

    fun seleccionarItem(index:Int){
        if (multiSeleccion){
            //Si el item ya estaba seleccionado y se vuelve a seleccionar se elimina de la lista de items seleccionados, si no lo esta se agrega
            if (itemsSeleccionados?.contains(index)!!){
                itemsSeleccionados?.remove(index)
            }else{
                itemsSeleccionados?.add(index)
            }

            notifyDataSetChanged()
        }
    }

    fun obtenerNumeroElementosSeleccionados(): Int {
        return itemsSeleccionados!!.count()
    }

    fun eliminarSeleccionados() {
        if (itemsSeleccionados?.count()!! > 0){
            var itemsEliminados = ArrayList<Platillo>()
            for (index in itemsSeleccionados!!){
                itemsEliminados.add(items?.get(index)!!)
            }
            //RemoveAll elimina todos los items pasados por argumento como arreglo
            items?.removeAll(itemsEliminados)
            itemsSeleccionados?.clear()
            notifyDataSetChanged()
        }
    }

    class ViewHolder(vista:View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista) , View.OnClickListener, View.OnLongClickListener{
        var vista = vista
        var imagen:ImageView? = null
        var nombre:TextView? = null
        var precio:TextView? = null
        var rating:RatingBar? = null
        var listener:ClickListener? = null
        var longClickListener:LongClickListener? = null

        init {
            imagen = vista.findViewById(R.id.ivFoto)
            nombre = vista.findViewById(R.id.tvPlatillo)
            precio = vista.findViewById(R.id.tvPrecio)
            rating = vista.findViewById(R.id.rbRating)
            this.listener = listener
            this.longClickListener = longClickListener
            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        //Cuando se haga click sobre un listener que sera el elemento seleccionado se reconcera
        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longClickListener?.longClick(v!!, adapterPosition)
            return true
        }
    }

}
