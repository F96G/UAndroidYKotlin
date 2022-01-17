package com.example.ejemplolistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = findViewById<ListView>(R.id.lista)
        var frutas:ArrayList<Fruta> = ArrayList()

        //Adaptador de lista de Array a ListaView
        //var adaptador = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, frutas)

        var adaptador = AdaptadorCustom(this,frutas)

        frutas.add(Fruta("Manzana", R.drawable.manzana))
        frutas.add(Fruta("Banana", R.drawable.platano))
        frutas.add(Fruta("Durazno", R.drawable.durazno))
        frutas.add(Fruta("Sandia", R.drawable.sandia))


         //Realiza el enlistado en pantalla
        lista.adapter = adaptador


        //Solo muestra que fruta se selecciono
        lista.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, frutas.get(position).nombre , Toast.LENGTH_SHORT).show()
        }
    }
}