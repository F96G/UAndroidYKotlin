package com.example.clima

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

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

        //La clase ciudades utiliza el tag para saber como actualizar los datos mostrados en pantalla.
        val ciudad = intent.getStringExtra("com.RK.Clima.ciudades.CIUDAD")

        //En caso de que se seleccione alguna de las ciudades se actualizan los valores correspondientes
            //Veo si tengo internet en caso de tenerlo llamo a la solicitud
            if (Network.hayRed(this)) {
                solicitudesHTPPVolley("https://api.openweathermap.org/data/2.5/weather?id="+ciudad+"&appid=ba3a38fdb8f8bfb01f3d25e491fa62df&units=metric&lang=es")
            } else {
                Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show()
        }



/*
        val ciudadCordoba = Ciudad("Cordoba", 21, "Lluvia")
        val ciudadVillaDelRosario = Ciudad("Villa del Rosario", 18, "Soleado")


        //En caso de que se seleccione alguna de las ciudades se actualizan los valores correspondientes
        if (ciudad == "ciudad-cordoba"){
            tvCiudad?.text=ciudadCordoba.nombre
            tvGrados?.text=ciudadCordoba.grados.toString()+"º"
            tvEstado?.text=ciudadCordoba.estado
            }else if(ciudad == "ciudad-villaDelRosario"){
            tvCiudad?.text=ciudadVillaDelRosario.nombre
            tvGrados?.text=ciudadVillaDelRosario.grados.toString()+"º"
            tvEstado?.text=ciudadVillaDelRosario.estado
        }

 */

    }


    private fun solicitudesHTPPVolley(url:String){
        val cola = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, url, {
                response ->
            try {
                //Log.d("solicitudHTTPVolley", response)

                val gson = Gson()
                val ciudad = gson.fromJson(response, Ciudad::class.java)
                //Log.d("GSON",ciudad.name)
                tvCiudad?.text=ciudad.name
                tvGrados?.text=ciudad.main?.temp.toString() + "º"
                tvEstado?.text=ciudad.weather?.get(0)!!.description


            }catch (e:Exception){

            }
        }, {  })

        cola.add(solicitud)
    }
}