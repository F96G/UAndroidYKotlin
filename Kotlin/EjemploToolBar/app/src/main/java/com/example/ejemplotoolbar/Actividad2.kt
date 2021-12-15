package com.example.ejemplotoolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar


class Actividad2 : AppCompatActivity() {
    var toolbar:Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad2)

        toolbar = findViewById(R.id.toolbar2)
        toolbar?.setTitle(R.string.app_name)

        setSupportActionBar(toolbar)

        //Agrego un boton para volver hacia atras, para que funcione en el manifiesto agrego parentActivityName
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //Asocia el template.xml a nuestro toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //Se implementa el menu
        menuInflater.inflate(R.menu.menu_actividad2, menu)
        return super.onCreateOptionsMenu(menu)
    }


}