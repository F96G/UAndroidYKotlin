package com.example.ejemplorecyclerview

import android.view.View

//Creo la interface para agreagar la funcion de OnClickListener a Adaptador ya que RecycleView no lo hace por defecto
interface ClickListener {

    fun onClick(vista: View, index:Int)
}