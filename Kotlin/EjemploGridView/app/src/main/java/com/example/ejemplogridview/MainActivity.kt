package com.example.ejemplogridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var grid:GridView = findViewById(R.id.grid)

        var frutas:ArrayList<Fruta> = ArrayList()

        frutas.add(Fruta("Manzana", R.drawable.manzana))
        frutas.add(Fruta("Banana", R.drawable.platano))
        frutas.add(Fruta("Sandia", R.drawable.sandia))
        frutas.add(Fruta("Durazno", R.drawable.durazno))
        frutas.add(Fruta("Manzana", R.drawable.manzana))
        frutas.add(Fruta("Banana", R.drawable.platano))
        frutas.add(Fruta("Sandia", R.drawable.sandia))
        frutas.add(Fruta("Durazno", R.drawable.durazno))
        frutas.add(Fruta("Manzana", R.drawable.manzana))
        frutas.add(Fruta("Banana", R.drawable.platano))
        frutas.add(Fruta("Sandia", R.drawable.sandia))
        frutas.add(Fruta("Durazno", R.drawable.durazno))

        var adaptador = AdaptadorCustom(this, frutas)
        grid.adapter = adaptador

        grid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,frutas.get(position).nombre,Toast.LENGTH_SHORT).show()
        }
    }
}