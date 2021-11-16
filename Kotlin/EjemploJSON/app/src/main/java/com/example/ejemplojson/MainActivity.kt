package com.example.ejemplojson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

//    var listaPersona:ArrayList<Persona>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var respuesta = "{ \"personas\" : [ " +
                "{" +
                " \"nombre\" : \"Marcos\" ," +
                " \"pais\" : \"México\" ," +
                " \"estado\" : \"soltero\" ," +
                " \"experiencia\" : 5}," +

                "{" +
                " \"nombre\" : \"Agustín\" ," +
                " \"pais\" : \"España\" ," +
                " \"estado\" : \"casado\" ," +
                " \"experiencia\" : 16}" +
                " ]" +
                " }"
        val gson = Gson()
        var res =gson.fromJson(respuesta, Personas::class.java)

        Log.d("GSONcount", res.personas?.count().toString())

        for (i in 0..res.personas!!.size)
            Log.d("NOMBRE",res.personas?.get(i)!!.nombre)

        /* Enlistar con Array
        val json = JSONObject(respuesta)
        val personas = json.getJSONArray("personas")


        listaPersona = ArrayList()

        for (i in 0..personas.length()){
            val nombre = personas.getJSONObject(i).getString("nombre")
            val pais = personas.getJSONObject(i).getString("pais")
            val estado = personas.getJSONObject(i).getString("estado")
            val experiencia = personas.getJSONObject(i).getInt("experiencia")

            //var persona = Persona(nombre,pais,estado, experiencia)

            listaPersona?.add(Persona(nombre,pais,estado, experiencia))
            Log.d("PERSONA",listaPersona?.get(i)!!.nombre )
            //Log.d("PERSONA", persona.nombre)
        }

         */


    }
}