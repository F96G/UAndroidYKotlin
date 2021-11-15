package com.example.clima

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var tvCiudad:TextView?=null
    var tvEstado:TextView?=null
    var tvGrados:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCiudad = findViewById(R.id.tV_Ciudad)
        tvEstado = findViewById(R.id.tV_Estado)
        tvGrados = findViewById(R.id.tV_Grados)

        var ciudad = intent.getStringExtra("com.RK.Clima.ciudades.CIUDAD")

        var ciudadCordoba = Ciudad("Cordoba", 21, "Lluvia")
        var ciudadVillaDelRosario = Ciudad("Villa del Rosario", 18, "Soleado")

        if (ciudad == "ciudad-cordoba"){
            tvCiudad?.text=ciudadCordoba.nombre
            tvGrados?.text=ciudadCordoba.grados.toString()+"º"
            tvEstado?.text=ciudadCordoba.estado
            }else if(ciudad == "ciudad-villaDelRosario"){
            tvCiudad?.text=ciudadVillaDelRosario.nombre
            tvGrados?.text=ciudadVillaDelRosario.grados.toString()+"º"
            tvEstado?.text=ciudadVillaDelRosario.estado
        }

    }
}